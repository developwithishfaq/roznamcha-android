package com.downloader.roznamcha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.downloader.roznamcha.core.prefs.PreferencesHelper
import com.downloader.roznamcha.presentation.navigation.Screen
import com.downloader.roznamcha.presentation.screens.MainNavHost
import com.downloader.roznamcha.ui.theme.RoznamchaTheme
import kotlinx.coroutines.flow.first
import org.koin.android.ext.android.inject

val LocalNavHostController = compositionLocalOf<NavHostController> {
    error("")
}

class MainActivity : ComponentActivity() {

    private val preferencesHelper: PreferencesHelper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var showScreen by remember { mutableStateOf(false) }
            var screen by remember { mutableStateOf<Screen>(Screen.CreateBusiness) }
            LaunchedEffect(true) {
                val id = preferencesHelper.businessIdFlow.first()
                screen = id?.run { Screen.Home } ?: Screen.CreateBusiness
                showScreen = true
            }
            RoznamchaTheme {
                if (showScreen) {
                    val navController = rememberNavController()
                    CompositionLocalProvider(LocalNavHostController provides navController) {
                        MainNavHost(
                            modifier = Modifier
                                .fillMaxSize(),
                            initScreen = screen
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RoznamchaTheme {
        Greeting("Android")
    }
}