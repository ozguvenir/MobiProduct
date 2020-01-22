package com.ridvan.mobiproduct.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ridvan.mobiproduct.R
import com.ridvan.mobiproduct.databinding.ActivityProductDetailBinding
import com.ridvan.mobiproduct.utilities.BUNDLE
import com.ridvan.mobiproduct.utilities.PRODUCT

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        binding.lifecycleOwner = this
        val bundle = intent.getBundleExtra(BUNDLE)
        binding.product = bundle?.getParcelable(PRODUCT)
    }
}