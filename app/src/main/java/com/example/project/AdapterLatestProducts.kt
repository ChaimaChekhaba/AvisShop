package com.example.project


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project.roomPersistence.Produit
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_latest_products.view.*


class AdapterLatestProducts (var latestProds : List<Produit>):
    RecyclerView.Adapter <AdapterLatestProducts.ProductHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = ProductHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.activity_latest_products, parent, false)
    )

    override fun getItemCount()= latestProds.size

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.bind(latestProds[position])
    }


    class ProductHolder (v :View): RecyclerView.ViewHolder(v) {

        private val imageV = v.imgProd
        private val textV = v.textProd

        fun bind(pro: Produit) {

            textV.text = pro.NomProduit
            Picasso.get().load(pro.ImageProduit).error(R.drawable.amazon_logo).fit().into(imageV)

        }

    }

}

