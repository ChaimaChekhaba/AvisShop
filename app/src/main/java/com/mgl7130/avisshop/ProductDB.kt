package com.mgl7130.avisshop

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Product::class), version = 1, exportSchema = false)
abstract class ProductDB : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: ProductDB? = null

        fun getInstance(context: Context): ProductDB {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): ProductDB {
            return Room.databaseBuilder(context, ProductDB::class.java, "products")
                .build()
        }
    }

}