package com.downloader.roznamcha.di

import com.downloader.roznamcha.domain.usecases.CreateBusinessUseCase
import com.downloader.roznamcha.domain.usecases.CreateKhataEntry
import com.downloader.roznamcha.domain.usecases.CreatePersonUseCase
import com.downloader.roznamcha.domain.usecases.CreatePurchaseUseCase
import com.downloader.roznamcha.domain.usecases.CreateRozNamchaUseCase
import com.downloader.roznamcha.domain.usecases.GetPersonByKhata
import com.downloader.roznamcha.presentation.business.BusinessViewModel
import com.downloader.roznamcha.presentation.screens.khata.KhataViewModel
import com.downloader.roznamcha.presentation.screens.purchases.PurchasesViewModel
import com.downloader.roznamcha.presentation.screens.roznamcha.RoznamchaViewModel
import com.downloader.roznamcha.presentation.sheets.persons.PersonToDealViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val shared_modules = module {
    viewModelOf(::RoznamchaViewModel)
    viewModelOf(::BusinessViewModel)
    viewModelOf(::PersonToDealViewModel)
    viewModelOf(::KhataViewModel)
    viewModelOf(::PurchasesViewModel)
    factoryOf(::CreateBusinessUseCase)
    factoryOf(::CreateRozNamchaUseCase)
    factoryOf(::CreateKhataEntry)
    factoryOf(::GetPersonByKhata)
    factoryOf(::CreatePersonUseCase)
    factoryOf(::CreatePurchaseUseCase)
}

val allModules = shared_modules + databaseModule + repositoryModule + storageModule
