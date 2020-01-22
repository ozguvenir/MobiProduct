package com.ridvan.mobiproduct.view.callback

import com.ridvan.mobiproduct.model.Product

interface ItemClickCallback {
    fun onClick(product: Product)
}
