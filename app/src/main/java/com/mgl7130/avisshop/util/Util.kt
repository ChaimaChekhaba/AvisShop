package com.mgl7130.avisshop.util

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.work.*
import com.google.gson.Gson
import com.mgl7130.avisshop.model.Product
import com.mgl7130.avisshop.retrofit.ProductApiService
import com.mgl7130.avisshop.retrofit.ServiceBuilder
import com.mgl7130.avisshop.view.ProductDetailsActivity
import com.mgl7130.avisshop.R


//check if there is network access
fun isOnline(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val n = cm.activeNetwork
        if (n != null) {
            val nc = cm.getNetworkCapabilities(n)
            //It will check for both wifi and cellular network
            return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        }
        return false
    } else {
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}

//when the network access, execute the delayed request synchronously
class ProductWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams){

    override fun doWork(): Result {

        val barcode = inputData.getString("CODE_BAR")
        val service = ServiceBuilder.retrofitInstance?.create(ProductApiService::class.java)
        val status: String
        var product : Product? = null

        val call = barcode?.let { service?.getData(it) }
        val body = call?.execute()?.body()

        if (body != null && body.product_name != "None"){
            body.barcode = barcode
            status = "200"
            product = body
        }
        else{
            status = "404"
        }

        val gson = Gson().toJson(product)
        val outputData = Data.Builder()
            .putString("status", status)
            .putString("product", gson)
            .build()

        return if (status == "200")
            Result.success(outputData)
        else
            Result.failure(outputData)
    }
}

//the worker for offline mode
fun launchWorker(barcode: String, lifecycle: LifecycleOwner){
    val barcodeData = Data.Builder().putString("CODE_BAR", barcode).build()

    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .setRequiresCharging(true)
        .setRequiresStorageNotLow(true)
        .setRequiresDeviceIdle(true)
        .build()

    val productWorker = OneTimeWorkRequestBuilder<ProductWorker>()
        .setInputData(barcodeData)
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance().enqueue(productWorker)

    WorkManager.getInstance().getWorkInfoByIdLiveData(productWorker.id)
        .observe(lifecycle, Observer { workInfo ->
            // Check if the current work's state is "successfully finished"
            if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                val status = workInfo.outputData.getString("status")
                if (status == "200"){
                    //send notification to the users
                    val productGson = workInfo.outputData.getString("product")
                    if (productGson != null) {
                        showNotification(productGson, lifecycle as Context)
                    }
                }
            }
        })
}

//show notification to the user
fun showNotification(productGson: String, context: Context){

    val product: Product = Gson().fromJson(productGson, Product::class.java)

    val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder

    val channelId = "com.mgl7130.avisshop"
    val description = "AvisShop Notification"

    val intent = Intent(context, ProductDetailsActivity::class.java)

    intent.putExtra("product", productGson)
    intent.action = productGson
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

    val pendingIntent = PendingIntent.getActivity(context, 0, intent,
        PendingIntent.FLAG_UPDATE_CURRENT)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        notificationChannel = NotificationChannel(channelId, description,
            NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.BLUE
        notificationChannel.enableVibration(false)
        notificationManager.createNotificationChannel(notificationChannel)

        builder = Notification.Builder(context, channelId)
            .setContentTitle("AvisShop")
            .setContentText("Nous avons trouvé le produit ${product.barcode}")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
            .setContentIntent(pendingIntent)
    }
    else{
        builder = Notification.Builder(context)
            .setContentTitle("AvisShop")
            .setContentText("Nous avons trouvé le produit ${product.barcode}")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    }
    notificationManager.notify(1, builder.build())
}
