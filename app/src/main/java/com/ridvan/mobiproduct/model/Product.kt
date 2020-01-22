package com.ridvan.mobiproduct.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val id: String,
    val categoryId: String,
    val name: String,
    val url: String,
    val description: String,
    val salePrice: SalePrice
) : Parcelable