package com.mgl7130.avisshop

import androidx.room.*

@Dao
interface ProductDao {
    // getting all the products for the history
    @Query("SELECT * FROM Product")
    suspend fun getAll(): List<Product>

    // inserting new products to the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg products: Product)

    // deleting the product from the database after a periode of time
    @Delete
    suspend fun delete(product: Product)
}