package com.example.project.roomPersistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = arrayOf(Produit::class), version = 1)

abstract class DBproduit : RoomDatabase () {

    abstract fun produitDAO() : ProduitDAO

    companion object {

        @Volatile
        private var INSTANCE: DBproduit? = null

        fun getInstance(context: Context): DBproduit = INSTANCE
                ?: synchronized(this) {
            INSTANCE
                    ?: createDatabase(context).also { INSTANCE = it }
        }

        private fun createDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext, DBproduit::class.java, "ProduitDB")
                        .build()
    }

}