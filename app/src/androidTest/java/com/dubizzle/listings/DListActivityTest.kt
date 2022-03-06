package com.dubizzle.listings

import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.dubizzle.listings.presentation.detail.DDetailsActivity
import com.dubizzle.listings.presentation.list.DListActivity
import com.dubizzle.listings.presentation.list.DListAdapter
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeoutException


@RunWith(AndroidJUnit4::class)
@LargeTest
class DListActivityTest {

    @get:Rule
    var rule: ActivityScenarioRule<DListActivity> = ActivityScenarioRule(
        DListActivity::class.java
    )

    @Before
    fun stubCameraIntent() {
        // Initializes Intents and begins recording intents.
        Intents.init()
    }

    @Test
    fun item_loaded_properly() {
        onView(withId(R.id.tvEmpty)).check(matches(isDisplayed()))
        onView(isRoot()).perform(waitForView(R.id.recycler, 10000)).check(matches(isDisplayed()))

        onView(withId(R.id.tvEmpty)).check(matches(not(isDisplayed())))

        onView(withId(R.id.recycler))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<DListAdapter.ViewHolder>(
                    2,
                    click()
                )
            )

        intended(hasComponent(DDetailsActivity::class.java.name))
        intended(hasExtraWithKey(DDetailsActivity.INTENT_EXTRA_PARAM_LISTING_CREATED_AT))
        intended(hasExtraWithKey(DDetailsActivity.INTENT_EXTRA_PARAM_LISTING_PRICE))
        intended(hasExtraWithKey(DDetailsActivity.INTENT_EXTRA_PARAM_LISTING_NAME))
        intended(hasExtraWithKey(DDetailsActivity.INTENT_EXTRA_PARAM_LISTING_IMAGE_URL))

        onView(withId(R.id.tvListingName)).check(matches(textViewHasValue()))
    }

    @After
    fun tearDown() {
        // Clears Intents state.
        Intents.release()
    }

}

fun waitForView(viewId: Int, timeout: Long): ViewAction {
    return object : ViewAction {
        override fun getConstraints() = isRoot()

        override fun getDescription(): String {
            return "wait for a specific view with id $viewId; during $timeout millis."
        }

        override fun perform(uiController: UiController, rootView: View) {
            uiController.loopMainThreadUntilIdle()
            val startTime = System.currentTimeMillis()
            val endTime = startTime + timeout
            val viewMatcher = withId(viewId)

            do {
                // Iterate through all views on the screen and see if the view we are looking for is there already
                for (child in TreeIterables.breadthFirstViewTraversal(rootView)) {
                    // found view with required ID
                    if (viewMatcher.matches(child) && child.isVisible) {
                        return
                    }
                }
                // Loops the main thread for a specified period of time.
                // Control may not return immediately, instead it'll return after the provided delay has passed and the queue is in an idle state again.
                uiController.loopMainThreadForAtLeast(100)
            } while (System.currentTimeMillis() < endTime) // in case of a timeout we throw an exception -&gt; test fails
            throw PerformException.Builder()
                .withCause(TimeoutException())
                .withActionDescription(this.description)
                .withViewDescription(HumanReadables.describe(rootView))
                .build()
        }
    }
}

fun textViewHasValue(): Matcher<View?> {
    return object : TypeSafeMatcher<View?>() {
        override fun describeTo(description: Description) {
            description.appendText("The TextView/EditText has value")
        }

        override fun matchesSafely(view: View?): Boolean {
            return if (view != null && view is TextView) {
                !TextUtils.isEmpty(view.text.toString())
            } else {
                false
            }
        }
    }
}