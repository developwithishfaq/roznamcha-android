package com.downloader.roznamcha.di

import com.downloader.roznamcha.presentation.screens.create_purchase.CreatePurchaseViewModel
import com.downloader.roznamcha.presentation.screens.roznamcha.RoznamchaViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val shared_modules = module {
    viewModelOf(::CreatePurchaseViewModel)
    viewModelOf(::RoznamchaViewModel)
}
val allModules = shared_modules + databaseModule + repositoryModule + storageModule
