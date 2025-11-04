package com.downloader.roznamcha.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Home : Screen()
    @Serializable
    data object CreatePurchase : Screen()
    @Serializable
    data object CreateBusiness : Screen()

}
