package com.ridvan.mobiproduct.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ridvan.mobiproduct.R
import com.ridvan.mobiproduct.model.Category
import com.ridvan.mobiproduct.network.MobiProductService
import com.ridvan.mobiproduct.utilities.BASE_URL
import com.ridvan.mobiproduct.utilities.TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ProductListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        retrofit()
    }

    fun retrofit() {
        val httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
            .apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }

        val okHttpclient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpclient)
            .build().create(MobiProductService::class.java).getCategoryAndProducts()
            .enqueue(object : Callback<List<Category>> {
                override fun onResponse(
                    call: Call<List<Category>>,
                    response: Response<List<Category>>
                ) {
                    if (response.isSuccessful) {
                    }
                }

                override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                }
            })
    }
}
