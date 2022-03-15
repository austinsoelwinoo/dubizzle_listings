package com.dubizzle.listings

import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
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
import java.util.*
import java.util.concurrent.TimeoutException
import kotlin.concurrent.schedule


@RunWith(AndroidJUnit4::class)
@LargeTest
class DListActivityTest {

    @get:Rule
    var rule: ActivityScenarioRule<DListActivity> = ActivityScenarioRule(
        DListActivity::class.java
    )

    @get:Rule
    val composeTestRule = createAndroidComposeRule(DListActivity::class.java)

    @Before
    fun setup() {
        // Initializes Intents and begins recording intents.
        Intents.init()
    }

    fun asyncTimer(delay: Long = 1000) {
        AsyncTimer.start(delay)
        composeTestRule.waitUntil(
            condition = { AsyncTimer.expired },
            timeoutMillis = delay + 1000
        )
    }

    @Test
    fun item_loaded_navigated_properly() {
        val button = composeTestRule.onNode(hasTestTag("LoadingListItemTestTag0"))
        button.assertIsDisplayed()
        asyncTimer(12000)
        button.assertDoesNotExist()

        val listingCard =
            composeTestRule.onNode(hasTestTag("ListingTestTag4878bf592579410fba52941d00b62f94"))
        listingCard.assertIsDisplayed()
        listingCard.performClick()

        intended(hasComponent(DDetailsActivity::class.java.name))
        intended(hasExtraWithKey(DDetailsActivity.INTENT_EXTRA_PARAM_LISTING))

        onView(withId(R.id.tvListingName)).check(matches(textViewHasValue()))
        onView(withId(R.id.tvListingPrice)).check(matches(textViewHasValue()))
        onView(withId(R.id.tvListingCreated)).check(matches(textViewHasValue()))
    }

    @After
    fun tearDown() {
        // Clears Intents state.
        Intents.release()
    }

}

object AsyncTimer {
    var expired = false
    fun start(delay: Long = 1000) {
        expired = false
        Timer().schedule(delay) {
            expired = true
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