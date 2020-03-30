package com.mgl7130.avisshop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_latest_product.*
import kotlinx.coroutines.runBlocking

class LatestProductActivity : BaseActivity(), ProductAdapter.onProductClickListener {
    override val TAG = "LatestProductActivity"
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: ProductAdapter
    private lateinit var mProducts: List<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val contentView = inflater.inflate(R.layout.activity_latest_product, null, false)
        mDrawerLayout.addView(contentView, 0)
        val actionBar = supportActionBar
        actionBar!!.title = "Historique des évaluations"

        mRecyclerView = my_recycler_view
        loadProduct()
        configureRecyclerView()
    }

    private fun configureRecyclerView() {
        mAdapter = ProductAdapter(mProducts, this)
        mRecyclerView!!.adapter = mAdapter
        mRecyclerView!!.layoutManager = LinearLayoutManager(this)
        mRecyclerView!!.setHasFixedSize(true)
    }

    private fun loadProduct(){

        runBlocking {
            val productDB = ProductDB.getInstance(this@LatestProductActivity)
            mProducts = productDB.productDao().getAll()

        }
    }

    override fun onProductClick(position: Int) {
        var product = mProducts.get(position)
        val intent = Intent(this, ProductDetailsActivity::class.java)
        intent.putExtra("product", product)
        startActivity(intent)
    }
}
