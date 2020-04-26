package com.mgl7130.avisshop.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mgl7130.avisshop.model.Product
import com.mgl7130.avisshop.model.ProductDB

open class LatestProductViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository : ProductRepository =
        ProductRepository(ProductDB.getInstance(application, viewModelScope).productDao())
    var mProducts: LiveData<List<Product>>? = mRepository.getProducts()

    init {
        mProducts = ProductDB.getInstance(application, viewModelScope).productDao().getAll()
    }
}