package com.example.hmtestcode

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
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
    fun fab_is_not_visible_initially() {
        lateinit var listState: LazyListState

        composeTestRule.setContent {
            listState = rememberLazyListState()

            ScrollToTopFab(
                listState = listState,
                products = fakePagingItems()
            )
        }

        composeTestRule
            .onNodeWithTag("ScrollToTopFab")
            .assertDoesNotExist()
    }

    @Test
    fun fab_becomes_visible_after_scroll() {
        lateinit var listState: LazyListState

        composeTestRule.setContent {
            listState = rememberLazyListState()

            ScrollToTopFab(
                listState = listState,
                products = fakePagingItems()
            )
        }

        // Scrolla programmatically
        composeTestRule.runOnIdle {
            runBlocking {
                listState.scrollToItem(10)
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithTag("ScrollToTopFab")
            .assertExists()
    }
}

@Composable
fun fakePagingItems(): LazyPagingItems<Any> {
    val flow = flowOf(PagingData.from(List(30) { Any() }))
    return flow.collectAsLazyPagingItems()
}