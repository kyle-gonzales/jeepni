package com.example.jeepni.feature.home

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.example.jeepni.MainActivity
import com.example.jeepni.util.TestTags.TestTags
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
//        composeTestRule.setContent {
//            JeepNiTheme {
//
//            }
//        }
    }

    @Test
    fun dummyTest() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.jeepni", appContext.packageName)
    }

    @Test
    fun click_button_deleteDailyStat() {
        val button = composeTestRule.onNodeWithTag(TestTags.DELETE_DAILY_STATS)

        // See if delete button exists or nah
        button.assertExists()

        // Click on button
        button.performClick()

        // TODO: assert that toast or some visual thing appears

    }

    @Test
    fun click_button_logDailyAnalytics() {
        val button = composeTestRule.onNodeWithTag(TestTags.LOG_DAILY_ANALYTICS)

        // See if delete button exists or nah
        button.assertExists()

        // Click on button
        button.performClick()

        // TODO: assert that the compose appears on screen

    }

    @Test
    fun click_button_drivingMode() {
        val button = composeTestRule.onNodeWithTag(TestTags.DRIVING_MODE)

        // See if delete button exists or nah
        button.assertExists()

        // Click on button
        button.performClick()

        // TODO: assert that the map appears on screen

    }

    @Test
    fun click_button_openDrawer() {
        val button = composeTestRule.onNodeWithTag(TestTags.MAIN_DRAWER)

        // See if delete button exists or nah
        button.assertExists()

        // Click on button
        button.performClick()

        // TODO: assert that the side drawer appears on screen


    }
}

