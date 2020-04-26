package com.mgl7130.avisshop.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mgl7130.avisshop.R
import com.mgl7130.avisshop.model.Product


class ProductAdapter internal constructor(onProductListener: onProductClickListener) :
    RecyclerView.Adapter<ProductViewHolder>() {

    var mProducts: List<Product> = emptyList()

    var mOnProductClickListener: onProductClickListener = onProductListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.product_item_layout, parent, false)
        return ProductViewHolder(view, mOnProductClickListener)
    }

    override fun onBindViewHolder(viewHolder: ProductViewHolder, position: Int) {
        viewHolder.updateWithProduct(mProducts[position])
    }

    override fun getItemCount(): Int {
        return mProducts.size
    }

    interface onProductClickListener{
        fun onProductClick(position: Int)

    }

    fun updateProducts(products: List<Product>){
        mProducts = products
        notifyDataSetChanged()
    }
}
