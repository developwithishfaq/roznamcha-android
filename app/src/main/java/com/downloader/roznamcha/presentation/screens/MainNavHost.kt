package com.downloader.roznamcha.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.downloader.roznamcha.LocalNavHostController
import com.downloader.roznamcha.R
import com.downloader.roznamcha.presentation.business.CreateBusinessScreen
import com.downloader.roznamcha.presentation.navigation.Screen
import com.downloader.roznamcha.presentation.screens.home.HomeScreen
import kotlinx.coroutines.delay

@Composable
fun MainNavHost(
    modifier: Modifier,
    initScreen: Screen
) {
    val navHostController: NavHostController = LocalNavHostController.current
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Screen.Splash
    ) {
        composable<Screen.Splash> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.bottle))
                val progress by animateLottieCompositionAsState(
                    composition,
                    iterations = 1,
                    restartOnPlay = true,
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LottieAnimation(
                        modifier = Modifier,
                        composition = composition,
                        progress = { progress },
                    )
                }
                LaunchedEffect(Unit) {
                    delay(1800)
                    navHostController.navigate(initScreen) {
                        popUpTo(Screen.Splash) {
                            inclusive = true
                        }
                    }
                }
            }
        }
        composable<Screen.Home> {
            HomeScreen()
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