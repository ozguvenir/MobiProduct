package com.ridvan.mobiproduct

import android.content.Intent
import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.ridvan.mobiproduct.view.adapter.ProductListAdapter
import com.ridvan.mobiproduct.view.ui.ProductListActivity
import kotlinx.android.synthetic.main.activity_product_list.*
import org.hamcrest.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ProductListActivityTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(ProductListActivity::class.java)

    @Test
    fun testRecyclerViewDisplay() {
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testRecyclerViewPerform() {
        val coordinatorLayout = Espresso.onView(
            Matchers.allOf(
                childAtPosition(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.recycler_view),
                        childAtPosition(
                            ViewMatchers.withClassName(Matchers.`is`("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                            0
                        )
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        SystemClock.sleep(2000)
        coordinatorLayout.perform(ViewActions.click())
    }

    @Test
    fun testRecyclerViewScroll() {
        val intent =
            Intent(
                InstrumentationRegistry.getInstrumentation().targetContext,
                ProductListActivity::class.java
            )
        val activity = InstrumentationRegistry.getInstrumentation().startActivitySync(intent.apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view))
            .perform(activity.recycler_view.adapter?.itemCount?.minus(
                1
            )?.let {
                RecyclerViewActions.scrollToPosition<ProductListAdapter.ProductViewHolder>(
                    it
                )
            })
    }

    private fun waitForAdapterChange(recyclerView: RecyclerView) {
        val latch =
            CountDownLatch(1)
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            recyclerView.adapter!!.registerAdapterDataObserver(
                object : RecyclerView.AdapterDataObserver() {
                    override fun onChanged() {
                        latch.countDown()
                    }

                    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                        latch.countDown()
                    }
                })
        }
        if (recyclerView.adapter!!.itemCount > 0) {
            return
        }
        MatcherAssert.assertThat(latch.await(10, TimeUnit.SECONDS), CoreMatchers.`is`(true))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>,
        position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent) &&
                        view == parent.getChildAt(position)
            }
        }
    }
}
