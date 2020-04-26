package com.mgl7130.avisshop.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {
    // getting all the products for the history
    @Query("SELECT * FROM Product")
    fun getAll(): LiveData<List<Product>>

    // inserting new product to the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg product: Product)

    // deleting the product from the database after a periode of time
    @Delete
    suspend fun delete(product: Product)

    // this query is for test only
    @Query("SELECT count(*) FROM Product")
    suspend fun getProductsNumber(): Int

    // delete all the products in the database
    @Query("DELETE FROM Product")
    suspend fun deleteAll()

    @Query("SELECT * FROM Product WHERE barcode = :barcode")
    suspend fun getProduct(vararg barcode: String): Product
}