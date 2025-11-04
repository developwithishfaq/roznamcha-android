package com.downloader.roznamcha.presentation.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.downloader.roznamcha.presentation.screens.roznamcha.RoznamchaScreen

@Composable
fun HomeScreen() {
    val pagerState = rememberPagerState { 3 }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp),
        bottomBar = {
            HomeBottomNav(pagerState.currentPage)
        }
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding(it)
        ) { it ->
            when (it) {
                0 -> RoznamchaScreen()
            }
        }
    }
}