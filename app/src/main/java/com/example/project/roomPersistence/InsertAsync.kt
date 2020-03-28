package com.example.project.roomPersistence


import android.content.Context
import android.os.AsyncTask


class InsertAsync  : AsyncTask<Produit, Void, Void>() {

    private lateinit var context: Context
     override fun doInBackground(vararg params: Produit?): Void? {
       DBproduit.getInstance(context.applicationContext)
               .produitDAO().insert(params[0])
        return null
    }


}