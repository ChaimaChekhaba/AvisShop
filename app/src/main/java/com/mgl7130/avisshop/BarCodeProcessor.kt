package com.mgl7130.avisshop

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

//barcode processing
class BarCodeProcessor (context: Context, codebar: String){
    //barcode value
    var mCodeBar = codebar
    var context = context

    //send the bar code to the server as the communication with cloud and websrvices is not done yet
    //we use a list of produts (as demonstration) loaded from json file (products.txt in assets)
    //we choose a random object from a list of 5 products available in products.txt (in the Asset folder)
    fun proces_bar_code(){

        val products = read_product_form_json()
        val rnds = (0..4).random()
        val product = products[rnds]
        //we update the code bar of the products
        //Rq: We could have products with the same information but the codebar is different
        product.barcode = mCodeBar
        send_result(product)
    }

    //send the product to ProductDetailsActivity
    fun send_result(product: Product){
        val intent = Intent(context, ProductDetailsActivity::class.java)
        intent.putExtra("product", product)
        context.startActivity(intent)
    }

    //read the products file
    fun read_product_form_json(): List<Product> {
        val jsonFileString = getJsonDataFromAsset(context, "products.txt")
        val gson = Gson()
        val listProductType = object : TypeToken<List<Product>>() {}.type
        val products: List<Product> = gson.fromJson(jsonFileString, listProductType)

        return products

    }
    //get the products file from the asset
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