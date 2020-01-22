package com.ridvan.mobiproduct.di

import com.ridvan.mobiproduct.viewmodel.ProductListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {
    fun inject(viewModel: ProductListViewModel)
}