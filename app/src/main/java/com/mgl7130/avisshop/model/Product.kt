package com.mgl7130.avisshop.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Product")
data class Product(
    @PrimaryKey var barcode: String,
    @ColumnInfo(name = "product_name") val product_name: String?,
    @ColumnInfo(name = "product_image") val product_image: String?,
    @ColumnInfo(name = "note") val note: String?,
    @ColumnInfo(name = "amazon_price") val amazon_price: String?,
    @ColumnInfo(name = "ebay_price") val ebay_price: String?,
    @ColumnInfo(name = "good_points") val good_points: String?,
    @ColumnInfo(name = "bad_points") val bad_points: String?,
    @ColumnInfo(name = "link_amazon") val link_amazon: String?,
    @ColumnInfo(name = "link_ebay") val link_ebay: String?

) : Serializable