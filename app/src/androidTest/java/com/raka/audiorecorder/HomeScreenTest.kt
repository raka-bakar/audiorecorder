package com.raka.audiorecorder

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This instrumentation test can only be run when the minifyEnabled is set false(currently true)
 * currently, there is no workaround on it
 */
@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testIfAllButtonsAreClickableandDisplayed() {
        composeRule.onNodeWithTag("tickerTag").assertIsDisplayed()
        composeRule.onNodeWithTag("recordingButtonTag").assertIsDisplayed()
        composeRule.onNodeWithTag("playButtonTag").assertIsDisplayed()
        composeRule.onNodeWithTag("playButtonTag").assertHasClickAction()
        composeRule.onNodeWithTag("recordingButtonTag").assertHasClickAction()
    }

    @Test
    fun testNavigationToRecordingScreen(){
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("recordingButtonTag").performClick()

        composeRule.waitForIdle()
        composeRule.onNodeWithTag("RecordingTitleTag").assertIsDisplayed()
    }
}