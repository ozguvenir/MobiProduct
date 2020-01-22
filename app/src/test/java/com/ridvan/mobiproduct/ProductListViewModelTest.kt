package com.ridvan.mobiproduct

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ridvan.mobiproduct.MockData.createCategoryAnProductList
import com.ridvan.mobiproduct.model.Category
import com.ridvan.mobiproduct.model.CategoryAndProduct
import com.ridvan.mobiproduct.network.MobiProductService
import com.ridvan.mobiproduct.utilities.NetworkState
import com.ridvan.mobiproduct.viewmodel.ProductListViewModel
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class ProductListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mobiProductService: MobiProductService

    private lateinit var viewModel: ProductListViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.viewModel = ProductListViewModel()
    }

    @Test
    fun testNull() {
        Mockito.`when`(mobiProductService.getCategoryAndProducts()).thenReturn(null)
        assertNotNull(viewModel.getCategories())
        assertNotNull(viewModel.getNetworkState())
    }

    @Test
    fun testItemListEmpty() {
        val result = Mockito.mock(Observer::class.java)
        viewModel.getCategories().observeForever(result as Observer<List<Category>>)
        viewModel.getProductList()
        Mockito.verifyNoMoreInteractions(mobiProductService)
    }

    @Test
    fun testLoadItems() {
        val observer = LoggingObserver<List<CategoryAndProduct>>()
        observer.value = createCategoryAnProductList()
        with(observer.value) {
            MatcherAssert.assertThat(this?.size, CoreMatchers.`is`(6))
            MatcherAssert.assertThat(this?.get(0)?.categoryName, CoreMatchers.`is`("Category1"))
            MatcherAssert.assertThat(this?.get(1)?.product?.name, CoreMatchers.`is`("Product1"))
            MatcherAssert.assertThat(this, CoreMatchers.`is`(CoreMatchers.notNullValue()))
            assertNotNull(this?.isNotEmpty())
        }
    }

    @Test
    fun testNetworkState() {
        val result = Mockito.mock(Observer::class.java)
        viewModel.getNetworkState().observeForever(result as Observer<NetworkState>)
        Mockito.`when`(mobiProductService.getCategoryAndProducts())
            .thenReturn(ArgumentMatchers.any())
        viewModel.getProductList()
        Mockito.verify(result).onChanged(NetworkState.LOADING)
    }

    private class LoggingObserver<T> : Observer<T> {
        var value: T? = null
        override fun onChanged(t: T?) {
            this.value = t
        }
    }
}