package banquemisr.challenge05.mostafa

import android.util.Log
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performScrollToNode
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import banquemisr.challenge05.mostafa.ui.theme.MostafaTheme
import kotlinx.coroutines.delay

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    @Test
    fun test_popular() {
        composeTestRule.setContent {
            MostafaTheme {
                MainScreen()
            }
        }
        composeTestRule.onNodeWithText("Popular",).assertIsDisplayed()
        composeTestRule.onNodeWithText("Now Playing",).assertIsDisplayed()
        composeTestRule.onNodeWithText("Upcoming",).assertIsDisplayed()
        composeTestRule.onNodeWithTag("LazyRow").assertIsDisplayed()
        composeTestRule.onNodeWithText("The Wild Robot").assertIsDisplayed()
        composeTestRule.onNodeWithText("The Wild Robot").onParent().performClick()
        composeTestRule.onNodeWithTag("title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Overview").assertIsDisplayed()
        composeTestRule.onNodeWithTag("DetailScreen").performScrollToNode(hasTestTag("Original Language"))
        composeTestRule.onNodeWithTag("Release Date").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Vote Average").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Original Language").assertIsDisplayed()
    }
    @Test
    fun test_get_now(){

            composeTestRule.setContent {
                MostafaTheme {
                    MainScreen()
                }
            }
            composeTestRule.onNodeWithText("Popular",).assertIsDisplayed()
            composeTestRule.onNodeWithText("Now Playing",).assertIsDisplayed()
            composeTestRule.onNodeWithText("Upcoming",).assertIsDisplayed()
            composeTestRule.onNodeWithText("Now Playing").performClick()
            composeTestRule.onNodeWithTag("LazyRow").assertIsDisplayed()
            composeTestRule.onNodeWithTag("LazyRow").performScrollToNode(hasText("The Substance"))
            composeTestRule.onNodeWithText("The Substance").assertIsDisplayed()
            composeTestRule.onNodeWithText("The Substance").onParent().performClick()
            composeTestRule.onNodeWithTag("title").assertIsDisplayed()
            composeTestRule.onNodeWithTag("Overview").assertIsDisplayed()
            composeTestRule.onNodeWithTag("DetailScreen").performScrollToNode(hasTestTag("Original Language"))
            composeTestRule.onNodeWithTag("Release Date").assertIsDisplayed()
            composeTestRule.onNodeWithTag("Vote Average").assertIsDisplayed()
            composeTestRule.onNodeWithTag("Original Language").assertIsDisplayed()
            composeTestRule.onNodeWithTag("backButton").performClick()
            composeTestRule.onNodeWithText("Popular",).assertIsDisplayed()
            composeTestRule.onNodeWithText("Now Playing",).assertIsDisplayed()

        }
    @Test
    fun test_get_upcoming(){

        composeTestRule.setContent {
            MostafaTheme {
                MainScreen()
            }
        }
        composeTestRule.onNodeWithText("Popular",).assertIsDisplayed()
        composeTestRule.onNodeWithText("Now Playing",).assertIsDisplayed()
        composeTestRule.onNodeWithText("Upcoming",).assertIsDisplayed()
        composeTestRule.onNodeWithText("Upcoming").performClick()
        composeTestRule.onNodeWithTag("LazyRow").assertIsDisplayed()
        composeTestRule.onNodeWithTag("LazyRow").performScrollToNode(hasText("The Substance"))
        composeTestRule.onNodeWithText("The Substance").assertIsDisplayed()
        composeTestRule.onNodeWithText("The Substance").onParent().performClick()
        composeTestRule.onNodeWithTag("title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Overview").assertIsDisplayed()
        composeTestRule.onNodeWithTag("DetailScreen").performScrollToNode(hasTestTag("Original Language"))
        composeTestRule.onNodeWithTag("Release Date").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Vote Average").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Original Language").assertIsDisplayed()
        composeTestRule.onNodeWithTag("backButton").performClick()
        composeTestRule.onNodeWithText("Popular",).assertIsDisplayed()
        composeTestRule.onNodeWithText("Now Playing",).assertIsDisplayed()

    }

    @Test
    fun test_fail(){

        composeTestRule.setContent {
            MostafaTheme {
                MainScreen()
            }
        }
        composeTestRule.onNodeWithText("Popular",).assertIsDisplayed()
        composeTestRule.onNodeWithText("Now Playing",).assertIsDisplayed()
        composeTestRule.onNodeWithText("Upcoming",).assertIsDisplayed()
        composeTestRule.onNodeWithTag("ErrorTest").assertIsDisplayed()
    }

}