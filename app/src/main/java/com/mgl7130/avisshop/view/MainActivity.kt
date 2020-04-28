package com.mgl7130.avisshop.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.mgl7130.avisshop.R
import com.mgl7130.avisshop.model.Product
import com.mgl7130.avisshop.model.ProductDB
import com.mgl7130.avisshop.util.launchWorker
import com.mgl7130.avisshop.util.isOnline
import com.mgl7130.avisshop.viewmodel.ProductRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.runBlocking

// home activity
class MainActivity : BaseActivity() {
    override val TAG = "MainActivity"

    // code bar value
    var mScannedResult: String = ""


    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //adding the main view to the menu view
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val contentView = inflater.inflate(R.layout.activity_main, null, false)
        mDrawerLayout.addView(contentView, 0)

        captureCodeBar.setOnClickListener {
            run {
                //set capture code bar activity
                val integrator = IntentIntegrator(this)
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
                integrator.setPrompt("Scanner un code à barre")
                integrator.setBeepEnabled(false)
                integrator.setBarcodeImageEnabled(true)
                integrator.setOrientationLocked(false)
                integrator.initiateScan()
            }
        }
        //when the code bar is entred manually in the edit text
        codeBarNumber.setOnEditorActionListener{ _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                if (codeBarNumber.text.length == 13 || codeBarNumber.text.length == 8){
                    //it could be a valid codebar
                    mScannedResult = codeBarNumber.text.toString()
                    send()
                }
                true
            } else {
                false
            }
        }

    }

    //handling the result sent from the code bar activity (of the library)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if(result != null){

            if(result.contents != null){
                //scan done successfully call the barcode processor to handle the bar code
                mScannedResult = result.contents
                send()

            } else {
                mScannedResult = "scan failed"
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {

        outState.putString("scannedResult", mScannedResult)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.let {
            mScannedResult = it.getString("scannedResult").toString()
        }
    }

    override fun onResume() {
        codeBarNumber.text.clear()
        super.onResume()
    }

    // send the barcode to the backend
    private fun send(){
        runBlocking {
            val mRepository = ProductRepository(ProductDB.getInstance(application, this).productDao())
            val statusProductPair = mRepository.getData(mScannedResult)

            when {
                statusProductPair.first == "200" -> statusProductPair.second?.let { sendResult(it) }
                statusProductPair.first == "404" -> {
                    // there is network access
                    if  (isOnline(applicationContext)) {
                        Toast.makeText(this@MainActivity, "Produit introuvable. " +
                                "Merci de réesseyer utlerieurement.", Toast.LENGTH_LONG).show()
                        codeBarNumber.text.clear()
                    }
                    else{

                        Toast.makeText(this@MainActivity, "Pas d'accès internet. " +
                                "Merci de vérifier votre connexion.", Toast.LENGTH_LONG).show()
                        //no network access call the work manager to perform the operation when network access is available
                        launchWorker(mScannedResult, this@MainActivity)
                        codeBarNumber.text.clear()
                    }

                }
                else -> {
                    Log.d("error", "Something went wrong")
                }
            }
        }
    }

    //send the product to ProductDetailsActivity
    private fun sendResult(product: Product){
        val productGson = Gson().toJson(product)
        val intent = Intent(applicationContext, ProductDetailsActivity::class.java)
        intent.putExtra("product", productGson)
        intent.action = productGson
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        applicationContext.startActivity(intent)
    }
}

