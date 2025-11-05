package com.downloader.roznamcha.di

import com.downloader.roznamcha.domain.usecases.CreateBusinessUseCase
import com.downloader.roznamcha.presentation.business.BusinessViewModel
import com.downloader.roznamcha.presentation.screens.create_purchase.CreatePurchaseViewModel
import com.downloader.roznamcha.presentation.screens.roznamcha.RoznamchaViewModel
import com.downloader.roznamcha.presentation.sheets.persons.PersonToDealViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val shared_modules = module {
    viewModelOf(::CreatePurchaseViewModel)
    viewModelOf(::RoznamchaViewModel)
    viewModelOf(::BusinessViewModel)
    viewModelOf(::PersonToDealViewModel)
    factoryOf(::CreateBusinessUseCase)
    factoryOf(::RoznamchaViewModel)

}
val allModules = shared_modules + databaseModule + repositoryModule + storageModule
