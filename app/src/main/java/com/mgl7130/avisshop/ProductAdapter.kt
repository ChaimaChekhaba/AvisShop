package com.mgl7130.avisshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class ProductAdapter internal constructor(private val products: List<Product>, onProductListener: onProductClickListener) :
    RecyclerView.Adapter<ProductViewHolder>() {

    var mOnProductClickListener: onProductClickListener

    init {
        mOnProductClickListener = onProductListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.product_item_layout, parent, false)
        return ProductViewHolder(view, mOnProductClickListener)
    }

    override fun onBindViewHolder(viewHolder: ProductViewHolder, position: Int) {
        viewHolder.updateWithProduct(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    interface onProductClickListener{
        fun onProductClick(position: Int)

    }
}
