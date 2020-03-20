package com.example.project


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_latest_products.view.*
import kotlinx.android.synthetic.main.activity_main.*


class AdapterLatestProducts (var latestProds : ArrayList<Product>):
    RecyclerView.Adapter <AdapterLatestProducts.ProductHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = ProductHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.activity_latest_products, parent, false)
    )

    override fun getItemCount()= 3

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.bind(latestProds[position])
    }


    class ProductHolder (v :View): RecyclerView.ViewHolder(v) {

        private val imageV = v.imgProd
        private val textV = v.textProd

        fun bind(pro: Product) {

            textV.text = pro.product_name
            Picasso.get().load("https://pores-dilates.com/wp-content/uploads/2020/02/rouleau-jade-utilisation.png").error(R.drawable.amazon_logo).fit().into(imageV)

        }

    }

}

