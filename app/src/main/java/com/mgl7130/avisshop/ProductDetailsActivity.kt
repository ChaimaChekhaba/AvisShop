package com.mgl7130.avisshop

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.coroutines.*

class ProductDetailsActivity : BaseActivity() {
    override val TAG = "ProductDetailsActivity"

    private lateinit var mProduct: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val contentView = inflater.inflate(R.layout.activity_product_details, null, false)
        mDrawerLayout.addView(contentView, 0)

        val actionBar = supportActionBar
        actionBar!!.title = "Résultat"

        mProduct = intent.getSerializableExtra("product") as Product
        loadProduct()

        purchase_button.setOnClickListener {
            // Initialize a new instance of
            val listItems = arrayOf("Amazon", "Ebay")
            val mBuilder = AlertDialog.Builder(this@ProductDetailsActivity)
            mBuilder.setTitle("Choisir un site pour l'achat")
            //mBuilder.setMessage("Choisir le site d'achat ?")
            mBuilder.setSingleChoiceItems(listItems, -1) { dialogInterface, i ->
                if (i == 0){
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.amazon.ca"))
                    startActivity(intent)
                }
                else if (i == 1){
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ebay.ca"))
                    startActivity(intent)
                }
            }
            // Set a positive button and its click listener on alert dialog
            mBuilder.setPositiveButton("OK"){dialog, which ->
                // Do something when user press the positive button
            }
            // Set the neutral/cancel button click listener
            mBuilder.setNeutralButton("Annuler") { dialog, which ->
                // Do something when click the neutral button
                dialog.cancel()
            }
            val mDialog = mBuilder.create()
            mDialog.show()
        }
    }

    private fun loadProduct(){
        product_name.text = mProduct.product_name
        note.text = """${mProduct.note}/10"""
        amazon_price.text = """${mProduct.amazon_price}$"""
        ebay_price.text = """${mProduct.ebay_price}$"""
        goodpoints.text = mProduct.good_points + mProduct.good_points + mProduct.good_points
        badpoints.text = mProduct.bad_points + mProduct.bad_points
        Picasso.get().load(mProduct.product_image).into(product_image)
    }
    //add the product to the database
    override fun onStop() {
        insertProduct()
        super.onStop()
    }

    fun insertProduct(){
        runBlocking {
            val productDB = ProductDB.getInstance(this@ProductDetailsActivity)
            productDB.productDao().insertAll(mProduct)
        }
    }

}
