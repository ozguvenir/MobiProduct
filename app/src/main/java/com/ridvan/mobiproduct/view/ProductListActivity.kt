package com.ridvan.mobiproduct.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.ridvan.mobiproduct.R
import com.ridvan.mobiproduct.databinding.ActivityProductListBinding
import com.ridvan.mobiproduct.viewmodel.ProductListViewModel

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding
    private lateinit var productListViewModel: ProductListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)
        productListViewModel = ViewModelProviders.of(this).get(ProductListViewModel::class.java)
        binding.viewModel = productListViewModel
        binding.lifecycleOwner = this

        if (savedInstanceState == null) {
            productListViewModel.getProductList()
        }
    }
}
