package com.mgl7130.avisshop.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [Product::class], version = 2, exportSchema = false)
abstract class ProductDB : RoomDatabase() {
    abstract fun productDao(): ProductDao

    private class ProductDBCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            instance?.let { database ->
                scope.launch {
                    val productDao = database.productDao()

                    // Delete all content here.
                    productDao.deleteAll()
                }
            }
        }
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: ProductDB? = null

        fun getInstance(context: Context, scope: CoroutineScope): ProductDB {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context, scope).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context, scope: CoroutineScope): ProductDB {
            return Room.databaseBuilder(context, ProductDB::class.java, "products")
                .addCallback(ProductDBCallback(scope))
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}