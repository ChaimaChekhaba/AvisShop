package com.example.project.roomPersistence

import androidx.room.*

@Dao
interface ProduitDAO {
    @Query ("Select * From produit_tab")
     fun getLastThree() : List<Produit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert (prod : Produit?)

    @Delete
     fun delete(prod : Produit)

    @Update
     fun update(vararg prods : Produit)
}