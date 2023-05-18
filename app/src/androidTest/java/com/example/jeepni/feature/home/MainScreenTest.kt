package com.example.jeepni.feature.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.jeepni.MainActivity
import com.example.jeepni.feature.home.nullstrings.NullReplacements
import com.example.jeepni.util.TestTags.TestTags
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
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
// Apparently not needed? IDK
    }

    @Test
    fun dummyTest() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.jeepni", appContext.packageName)
    }

    @Test
    fun click_button_deleteDailyStat() {
        val button = composeTestRule.onNodeWithTag(TestTags.BUTTON_DELETE_DAILY_STATS)

        // See if delete button exists or nah
        button.assertIsDisplayed()

        // Click on button
        button.performClick()

        // TODO: assert that toast or some visual thing appears

    }

    @Test
    fun click_button_logDailyAnalytics_save() {
        val button = composeTestRule.onNodeWithTag(TestTags.BUTTON_LOG_DAILY_ANALYTICS)

        // See if delete button exists or nah
        button.assertExists()

        // Click on button
        button.performClick()

        // Check if the compose appears
        composeTestRule.onNodeWithText("Log Daily Analytics").assertIsDisplayed()

        // Enter inputs
        composeTestRule.onNodeWithText("Salary").performTextReplacement("369")
        composeTestRule.onNodeWithText("Fuel Cost").performTextReplacement("34")

        // Click on the Save button
        composeTestRule.onNodeWithText("Save").performClick()

        // TODO: Check if toast appears
//        onView(withText("saved"))
//            .inRoot(ToastMatcher())
//            .check(ViewAssertions.matches(isDisplayed()))

//        onView(withText("saved"))
//            .inRoot(ToastMatchers.isToast())
//            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun click_button_drivingMode() {
        val button = composeTestRule.onNodeWithTag(TestTags.BUTTON_DRIVING_MODE)

        // See if delete button exists or nah
        button.assertExists()

        // Click on button
        button.performClick()

        // Assert that map appears on screen
        composeTestRule.onNodeWithTag(TestTags.CONTENT_MAP).assertIsDisplayed()
        // TODO: Check if Map is working properly

        // TODO: Check if timer is working

        // TODO: Check if distance is "working"

    }

    @Test
    fun click_button_openDrawer() {
        val button = composeTestRule.onNodeWithTag(TestTags.BUTTON_MAIN_DRAWER)

        // See if the drawer button exists
        button.assertIsDisplayed()

        // Click on button
        button.performClick()

        // Assert that side drawer can be seen
        composeTestRule.onNodeWithTag(TestTags.CONTENT_DRAWER).assertIsDisplayed()

        // Check if null email is not shown
        assertFalse(
            composeTestRule.onNodeWithTag(TestTags.CONTENT_DRAWER_EMAIL).fetchSemanticsNode()
                .toString() == NullReplacements.TEXT_EMAIL_NOT_FOUND
        )
    }
}

