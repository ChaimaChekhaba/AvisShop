package com.mgl7130.avisshop

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

//to be completed
class BarCodeProcessor (context: Context, codebar: String){
    var mCodeBar = codebar
    var context = context

    //send the bar code to the server
    //this part is not functional it's just an example loaded from json file (products.txt in assets)
    //we choose a random object from a list of 5 products available in products.txt
    fun proces_bar_code(){

        var products = read_product_form_json()
        val rnds = (0..4).random()
        var product = products[rnds]
        product.barcode = mCodeBar
        send_result(product)
    }

    fun send_result(product: Product){
        var intent = Intent(context, ProductDetailsActivity::class.java)
        intent.putExtra("product", product)
        context.startActivity(intent)
    }

    fun read_product_form_json(): List<Product> {
        val jsonFileString = getJsonDataFromAsset(context, "products.txt")
        Log.i("data", jsonFileString)

        val gson = Gson()
        val listProductType = object : TypeToken<List<Product>>() {}.type

        var products: List<Product> = gson.fromJson(jsonFileString, listProductType)
        products.forEachIndexed { idx, product -> Log.i("data", "> Item $idx:\n$product") }

        return products

    }

    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}