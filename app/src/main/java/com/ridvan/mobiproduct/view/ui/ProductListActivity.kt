package com.ridvan.mobiproduct.view.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.ridvan.mobiproduct.R
import com.ridvan.mobiproduct.databinding.ActivityProductListBinding
import com.ridvan.mobiproduct.model.Category
import com.ridvan.mobiproduct.model.Product
import com.ridvan.mobiproduct.utilities.BUNDLE
import com.ridvan.mobiproduct.utilities.NetworkState
import com.ridvan.mobiproduct.utilities.PRODUCT
import com.ridvan.mobiproduct.utilities.TypeConverter
import com.ridvan.mobiproduct.view.adapter.ProductListAdapter
import com.ridvan.mobiproduct.view.callback.ItemClickCallback
import com.ridvan.mobiproduct.viewmodel.ProductListViewModel

class ProductListActivity : AppCompatActivity() {


    private lateinit var binding: ActivityProductListBinding
    private lateinit var productListViewModel: ProductListViewModel
    private lateinit var productListAdapter: ProductListAdapter
    private var isLoading = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)
        productListViewModel = ViewModelProviders.of(this).get(ProductListViewModel::class.java)
        binding.viewModel = productListViewModel
        binding.lifecycleOwner = this

        this.setRecyclerViewAdapter()
        this.setViewModelObservers()
        if (savedInstanceState == null) {
            productListViewModel.getProductList()
        }
    }

    private fun setRecyclerViewAdapter() {
        productListAdapter = ProductListAdapter(object : ItemClickCallback {
            override fun onClick(product: Product) {
                Bundle().apply {
                    val intent = Intent(binding.root.context, ProductDetailActivity::class.java)
                    val bundle = Bundle()
                    bundle.putParcelable(PRODUCT, product)
                    intent.putExtra(BUNDLE, bundle)
                    startActivity(intent)
                }

            }
        })

        binding.recyclerView.adapter = productListAdapter
        val layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        layoutManager.scrollToPosition(0)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (productListAdapter.getItemViewType(position)) {
                    0 -> 2
                    else -> 1
                }
            }

        }
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun setViewModelObservers() {
        productListViewModel.getCategories().observe(this,
            Observer<List<Category>> { categories ->
                if (categories != null) {
                    productListAdapter.updateCategoryAndProductList(
                        TypeConverter.getCategoryAndProductList(
                            categories, productListViewModel.productList
                        )
                    )
                }
            })

        productListViewModel.getNetworkState().observe(this,
            Observer { networkState ->
                binding.progressBar.visibility =
                    if (networkState == NetworkState.LOADING) View.VISIBLE else View.GONE

                isLoading = networkState == NetworkState.LOADING
            })
    }
}
