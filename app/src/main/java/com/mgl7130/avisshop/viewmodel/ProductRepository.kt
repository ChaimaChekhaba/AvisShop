package com.mgl7130.avisshop.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import com.mgl7130.avisshop.business.ProductApiService
import com.mgl7130.avisshop.business.ServiceBuilder
import com.mgl7130.avisshop.model.Product
import com.mgl7130.avisshop.model.ProductDao
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


open class ProductRepository (productDao: ProductDao){

    private val mProductDao: ProductDao = productDao
    private val mProducts : LiveData<List<Product>>

    private val service = ServiceBuilder.retrofitInstance?.create(ProductApiService::class.java)

    init{
        mProducts = mProductDao.getAll()
    }

    suspend fun insert(product: Product) {
        mProductDao.insert(product)
    }

    suspend fun delete(product: Product){
        mProductDao.delete(product)
    }

    fun getProducts(): LiveData<List<Product>>? {
        return mProducts
    }

    // this function just for test
    fun getNumberOfProducts(): Int? {
        return mProducts.value?.size

    }

    //get the product with barcode from the database
    private suspend fun getProduct(barcode: String): Product{
        return mProductDao.getProduct(barcode)
    }

    //get the product from the database or the network
    fun getData(barcode: String): Product {

        lateinit var product: Product
        runBlocking {
            product = getProduct(barcode)
        }

        //the product doesn't exist in the database
        if (product == null){
            val call = service?.getData(barcode)
            Log.d("serivce", "call")
            product = Product("None",
                "None", "None",
                "0", "0.0","0.0",
                "None", "None",
                "None", "None")
            call?.enqueue(object : Callback<Product> {
                override fun onFailure(call: Call<Product>, t: Throwable) {
                    Log.d("onFailure", t.localizedMessage)
                }

                override fun onResponse(call: Call<Product>, response: Response<Product>) {
                    Log.d("Respone",  response.message())
                    val body = response.body()
                    body?.barcode = barcode
                    if (body != null) {
                        product = body
                    }
                }
            })
        }

        return product
    }
}