package com.example.booklibrary.ui.home.fragment

import android.os.SystemClock
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.booklibrary.ui.activity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.booklibrary.R
import org.junit.Before


@RunWith(AndroidJUnit4::class)
@LargeTest
class BookListFragmentTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    //val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        val scenario = launchFragmentInContainer<BookListFragment>()
    }

    @Test
    fun test() {

        //onView(withId(R.id.inputEditText)).perform(typeText("Agatha Christie"), closeSoftKeyboard())

        onView(withId(R.id.button)).perform(click())

        //SystemClock.sleep(2000)

        // Check that a list of results is displayed
        //onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))

        // Optionally, check that a specific book is in the list
        // Note that this requires the book to always be in the results for "Agatha Christie"
        // onView(withText("And Then There Were None")).check(matches(isDisplayed()))

    }
}