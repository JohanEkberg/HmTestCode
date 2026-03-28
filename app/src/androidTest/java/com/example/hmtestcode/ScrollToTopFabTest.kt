package com.example.hmtestcode

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.hmtestcode.ui.components.ScrollToTopFab
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest

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
class ScrollToTopFabTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun initiallyDoesNotExist() {
        // Start the screen with index 0, Fab should not be visible
        setupProductScreenHelper(initialIndex = 0)

        // Verify that the Fab is not visible
        composeTestRule.onNodeWithTag("ScrollToTopFab").assertDoesNotExist()
    }

    @Test
    fun appearsWhenScrollingDown() {
        // Start the screen with an index that trigger Fab visibility
        setupProductScreenHelper(initialIndex = 12)

        // Verify that the Fab is shown
        composeTestRule.onNodeWithTag("ScrollToTopFab").assertIsDisplayed()
    }

    @Test
    fun clickingScrollsToTop() {
        // Start with a high index (far down in the list)
        setupProductScreenHelper(initialIndex = 20)

        // Click the Fab
        composeTestRule.onNodeWithTag("ScrollToTopFab").performClick()

        // Verify that the list has scrolled to the top.
        composeTestRule.onNodeWithText("Product 0").assertIsDisplayed()

        // Verify that the Fab has disappeared
        composeTestRule.onNodeWithTag("ScrollToTopFab").assertDoesNotExist()
    }

    private fun setupProductScreenHelper(initialIndex: Int) {
        composeTestRule.setContent {
            val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)

            // Create an empty flow of PagingData
            val emptyMockProducts: LazyPagingItems<String> = flowOf(
                PagingData.empty<String>()
            ).collectAsLazyPagingItems()

            Box {
                // Create a mock items list of 50 elements
                LazyColumn(state = listState) {
                    items(50) { index ->
                        Text(text = "Product $index", modifier = Modifier.height(100.dp))
                    }
                }

                // Call the composable to test
                ScrollToTopFab(
                    listState = listState,
                    products = emptyMockProducts,
                    pageSize = 4
                )
            }
        }
    }
}