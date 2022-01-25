package com.example.ntor.activities

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ntor.R
import com.example.ntor.presentation.main.progress.detail.RunDetailFragment
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CameraActivityTest {

private lateinit var scenario : FragmentScenario<RunDetailFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_MaterialComponents)
        scenario.moveToState(Lifecycle.State.STARTED)

    }

    @After
    fun tearDown() {

    }



    @Test
    fun test_name_fragment_button_enabled_when_edit_text_is_not_empty() {
//        onView(withId(R.id.workoutNameText)).perform(typeText("myname"))
//        Espresso.closeSoftKeyboard()
//        onView(withId(R.id.confirmNameButton)).check(matches(isEnabled()))
    }


}