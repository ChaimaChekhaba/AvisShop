package com.mgl7130.avisshop.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.mgl7130.avisshop.viewmodel.ProductRepository
import com.mgl7130.avisshop.R
import com.mgl7130.avisshop.model.Product
import com.mgl7130.avisshop.model.ProductDB
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.coroutines.*

@Suppress("PLUGIN_WARNING")
class ProductDetailsActivity : BaseActivity() {
    override val TAG = "ProductDetailsActivity"

    private lateinit var mProduct: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val contentView = inflater.inflate(R.layout.activity_product_details, null, false)
        mDrawerLayout.addView(contentView, 0)

        //change the title of the action bar
        val actionBar = supportActionBar
        actionBar!!.title = "Résultat"
        //get the product set from the barcodeprocessor
        mProduct = intent.getSerializableExtra("product") as Product

        //insert the product to the database
        insertProduct()
        loadProduct()

        purchase_button.setOnClickListener {
            // the alertdialog referencing amazon and ebay websites
            val listItems = arrayOf("Amazon", "Ebay")
            val mBuilder = AlertDialog.Builder(it.context)
            mBuilder.setTitle("Choisir un site pour l'achat")
            var intent : Intent? = null

            mBuilder.setSingleChoiceItems(listItems, -1) { _, i ->
                if (i == 0){
                    intent = Intent(Intent.ACTION_VIEW, Uri.parse(mProduct.link_amazon))
                }
                else if (i == 1){
                    intent = Intent(Intent.ACTION_VIEW, Uri.parse(mProduct.link_amazon))
                }

            }
            // Set a positive button and its click listener on alert dialog
            mBuilder.setPositiveButton("OK"){ _, _ ->
                startActivity(intent)
            }
            // Set the neutral/cancel button click listener
            mBuilder.setNegativeButton("Annuler") { dialog, _ ->
                dialog.cancel()
            }
            val mDialog = mBuilder.create()
            mDialog.show()
        }
    }

    //updating the product in the layout fields
    @SuppressLint("SetTextI18n")
    private fun loadProduct(){
        product_name.text = mProduct.product_name
        note.text = """${mProduct.note}/10"""
        amazon_price.text = """${mProduct.amazon_price}$"""
        ebay_price.text = """${mProduct.ebay_price}$"""
        goodpoints.text = mProduct.good_points + mProduct.good_points + mProduct.good_points
        badpoints.text = mProduct.bad_points + mProduct.bad_points
        Picasso.get().load(mProduct.product_image).into(product_image)
    }

    //inserting the product in the database
    private fun insertProduct(){
        runBlocking {
            val mRepository = ProductRepository(ProductDB.getInstance(application, this).productDao())
            mRepository.insert(mProduct)
        }

    }
}
