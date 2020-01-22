package com.ridvan.mobiproduct.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ridvan.mobiproduct.di.AppComponent
import com.ridvan.mobiproduct.di.AppModule
import com.ridvan.mobiproduct.di.DaggerAppComponent
import com.ridvan.mobiproduct.model.Category
import com.ridvan.mobiproduct.model.CategoryAndProduct
import com.ridvan.mobiproduct.network.MobiProductService
import com.ridvan.mobiproduct.utilities.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ProductListViewModel : ViewModel() {
    @Inject
    lateinit var mobiProductService: MobiProductService

    private val injector: AppComponent = DaggerAppComponent
        .builder()
        .appModule(AppModule)
        .build()

    init {
        injector.inject(this)
    }

    private val categories: MutableLiveData<List<Category>> = MutableLiveData()
    private val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    val productList: ArrayList<CategoryAndProduct> = arrayListOf()

    fun getProductList() {
        networkState.value = NetworkState.LOADING
        val call = mobiProductService.getCategoryAndProducts()
        call.enqueue(object : Callback<List<Category>> {
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                if (response.isSuccessful) {
                    assert(response.body() != null)
                    val categoryList: List<Category>? = response.body()
                    categories.value = categoryList
                    networkState.value = NetworkState.DONE
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                categories.value = null
                networkState.value = NetworkState.ERROR
            }
        })
    }

    fun getCategories(): MutableLiveData<List<Category>> {
        return categories
    }

    fun getNetworkState(): MutableLiveData<NetworkState> {
        return networkState
    }
}