package com.downloader.roznamcha.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.downloader.roznamcha.presentation.screens.roznamcha.RoznamchaScreen
import com.downloader.roznamcha.presentation.sheets.persons.PersonBottomSheet
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState { 3 }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp),
        bottomBar = {
            HomeBottomNav(pagerState.currentPage) {
                coroutineScope.launch {
                    pagerState.scrollToPage(it)
                }
            }
        }
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding(it)
        ) { it ->
            when (it) {
                0 -> RoznamchaScreen()
                1 -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        var showSheet by remember { mutableStateOf(false) }
                        Button(onClick = { showSheet = true }) {
                            Text("Select Person")
                        }
                        if (showSheet) {
                            PersonBottomSheet(
                                onPersonSelected = {
                                    showSheet = false
                                },
                                onDismiss = {
                                    showSheet = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}