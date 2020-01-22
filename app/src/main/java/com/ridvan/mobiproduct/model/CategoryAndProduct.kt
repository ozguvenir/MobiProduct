package com.ridvan.mobiproduct.model

import com.ridvan.mobiproduct.utilities.ItemType

data class CategoryAndProduct(
    val type: ItemType,
    val product: Product? = null,
    val categoryName: String? = null
)