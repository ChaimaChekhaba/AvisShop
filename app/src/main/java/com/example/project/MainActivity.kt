package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val product = Product()
        product.product_name = "Acer Predator Triton 500 Pro Gaming Notebook/ 15.6 FHD 144Hz IPS Display/ Ci7 9750H/ RTX 2060 6GB/ 16GB/512 SSD/Win 10, Black"
        product.product_image = "https://images-na.ssl-images-amazon.com/images/I/71hYEdeW0XL._AC_SL1500_.jpg"
        product.note = 10
        loadProduct(product)
    }

    private fun loadProduct(product: Product){
        Picasso.get().load(product.product_image).into(product_image)
        product_name.text = product.product_name
    }
}
