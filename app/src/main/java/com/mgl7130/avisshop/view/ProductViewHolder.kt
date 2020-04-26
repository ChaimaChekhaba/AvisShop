package com.mgl7130.avisshop.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mgl7130.avisshop.model.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_item_layout.view.*


class ProductViewHolder(itemView: View, onProductListener: ProductAdapter.onProductClickListener)
    : RecyclerView.ViewHolder(itemView), View.OnClickListener{
    var product_name: TextView = itemView.product_name
    var product_image: ImageView = itemView.product_image_item
    var mOnProductListener: ProductAdapter.onProductClickListener = onProductListener

    init {
        itemView.setOnClickListener(this)
    }

    fun updateWithProduct(product: Product?) {
        if (product != null) {
            product_name.text = product.product_name
            Picasso.get().load(product.product_image).into(product_image)
        }
    }

    override fun onClick(v: View?) {
        mOnProductListener.onProductClick(adapterPosition)
    }
}