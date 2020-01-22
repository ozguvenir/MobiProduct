package com.ridvan.mobiproduct

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.rule.ActivityTestRule
import com.ridvan.mobiproduct.view.ui.ProductDetailActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ProductDetailActivityTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(ProductDetailActivity::class.java)

    @Test
    fun testPhotoView() {
        Espresso.onView(ViewMatchers.withId(R.id.fullSizeImage))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testProductView() {
        Espresso.onView(ViewMatchers.withId(R.id.product_name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testAmountView() {
        Espresso.onView(ViewMatchers.withId(R.id.product_amount))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}