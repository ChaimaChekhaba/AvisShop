package com.mgl7130.avisshop.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mgl7130.avisshop.*
import com.mgl7130.avisshop.model.Product
import com.mgl7130.avisshop.viewmodel.LatestProductViewModel
import kotlinx.android.synthetic.main.activity_latest_product.*

class LatestProductActivity : BaseActivity(),
    ProductAdapter.onProductClickListener {
    override val TAG = "LatestProductActivity"
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: ProductAdapter
    private lateinit var mViewModel: LatestProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val contentView = inflater.inflate(R.layout.activity_latest_product, null, false)
        mDrawerLayout.addView(contentView, 0)

        val actionBar = supportActionBar
        actionBar!!.title = "Historique des évaluations"

        configureRecyclerView()
        configureViewModel()
        showNoProductsToast()
    }

    //recyclerview and ViewModel configuration
    private fun configureRecyclerView() {

        mRecyclerView = my_recycler_view
        mAdapter = ProductAdapter(this)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.setHasFixedSize(true)

    }

    //viewmodel configuration
    private fun configureViewModel(){
        mViewModel = ViewModelProvider(this@LatestProductActivity).
            get(LatestProductViewModel::class.java)
        mViewModel.mProducts?.observe(this, Observer<List<Product>> { products ->
            // Update the cached copy of the words in the adapter.
            products?.let {
                mAdapter.updateProducts(it)
            }
        })
    }

    //managing the click on the recyclerview items
    override fun onProductClick(position: Int) {
        val product = mAdapter.getItemId(position)
        val intent = Intent(this, ProductDetailsActivity::class.java)
        intent.putExtra("product", product)
        startActivity(intent)
        finish()
    }

    override fun onResume() {

        showNoProductsToast()
        super.onResume()
    }

    private fun showNoProductsToast(){
        if (mAdapter.itemCount == 0){
            Toast.makeText(this, "Vous n'avez pas encore évalué des produits",
                Toast.LENGTH_SHORT).show()
        }
    }
}
