package com.ridvan.mobiproduct.di

import com.ridvan.mobiproduct.network.MobiProductService
import com.ridvan.mobiproduct.utilities.BASE_URL
import com.ridvan.mobiproduct.utilities.TIMEOUT
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object AppModule {
    private val httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        .apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

    private val okHttpclient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    internal fun provideMyAppService(retrofit: Retrofit): MobiProductService {
        return retrofit.create(MobiProductService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpclient)
            .build()
    }
}