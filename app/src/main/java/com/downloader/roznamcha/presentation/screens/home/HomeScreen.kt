package com.downloader.roznamcha.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.downloader.roznamcha.presentation.screens.khata.KhataScreen
import com.downloader.roznamcha.presentation.screens.purchases.PurchasesScreen
import com.downloader.roznamcha.presentation.screens.roznamcha.RoznamchaScreen
import com.downloader.roznamcha.presentation.sheets.persons.PersonBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState { 3 }
    var showSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "RozNamcha Manager",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                tonalElevation = 4.dp
            ) {
                val items = listOf(
                    NavItem("Roznamcha", Icons.Default.Home),
                    NavItem("Khata", Icons.Default.Assignment),
                    NavItem("Purchases", Icons.Default.Widgets)
                )

                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch { pagerState.animateScrollToPage(index) }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label
                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                fontSize = 13.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            indicatorColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) { page ->
            when (page) {
                0 -> RoznamchaScreen()
                1 -> KhataScreen()
                2 -> PurchasesScreen()
            }
        }
    }
}

@Composable
fun PersonScreen(showSheet: Boolean, onShowSheetChange: (Boolean) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { onShowSheetChange(true) },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text("Select Person", fontSize = 16.sp)
        }

        if (showSheet) {
            PersonBottomSheet(
                onPersonSelected = { onShowSheetChange(false) },
                onDismiss = { onShowSheetChange(false) }
            )
        }
    }
}

data class NavItem(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

/*

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
}*/
