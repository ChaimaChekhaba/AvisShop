package com.example.project.roomPersistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "produit_tab")

data class Produit (
         // var pid : Int = 0,
         @PrimaryKey @ColumnInfo (name="Nom_Produit") var NomProduit : String ="",
        @ColumnInfo (name="Image_Produit") var ImageProduit : String=""
)
