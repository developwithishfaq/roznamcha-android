package com.downloader.roznamcha.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.downloader.roznamcha.LocalNavHostController
import com.downloader.roznamcha.presentation.business.CreateBusinessScreen
import com.downloader.roznamcha.presentation.navigation.Screen
import com.downloader.roznamcha.presentation.screens.create_purchase.CreatePurchaseScreen
import com.downloader.roznamcha.presentation.screens.home.HomeScreen

@Composable
fun MainNavHost(
    modifier: Modifier,
) {
    val navHostController: NavHostController = LocalNavHostController.current
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Screen.CreateBusiness
    ) {
        composable<Screen.Home> {
            HomeScreen()
        }
        composable<Screen.CreatePurchase> {
            CreatePurchaseScreen()
        }
        composable<Screen.CreateBusiness> {
            CreateBusinessScreen(moveToNextScreen = {
                navHostController.navigate(Screen.Home) {
                    popUpTo<Screen.CreateBusiness> {
                        inclusive = true
                    }
                }
            })
        }
    }
}