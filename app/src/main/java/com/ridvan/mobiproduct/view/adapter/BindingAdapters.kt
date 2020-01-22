package com.ridvan.mobiproduct.view.adapter

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ridvan.mobiproduct.R
import com.ridvan.mobiproduct.utilities.BASE_URL

@BindingAdapter("imageUrl")
fun imageFromUrl(view: ImageView, imageUrl: String?) {
    if (imageUrl != null && imageUrl.isNotEmpty()) {
        Glide.with(view.context)
            .load(BASE_URL + imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(R.drawable.default_product)
            .into(view)
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("formatProductName")
fun formatProductName(view: TextView, productName: String?) {
    view.text = "Product Name: $productName"
}

@SuppressLint("SetTextI18n")
@BindingAdapter(value = ["amount", "currency"], requireAll = false)
fun amountCurrency(view: TextView, amount: String?, currency: String?) {
    if (amount != null && currency != null) {
        view.text = "Amount: " + String.format("%s %s", amount, currency)
    }
}