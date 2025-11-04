package com.downloader.roznamcha.presentation.business

data class BusinessUiState(
    val businessId: String = "",
    val name: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val message: String? = null,
)
