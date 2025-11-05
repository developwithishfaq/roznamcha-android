package com.downloader.roznamcha.di

import com.downloader.roznamcha.data.repository.BusinessRepository
import com.downloader.roznamcha.data.repository.EmployeeRepository
import com.downloader.roznamcha.data.repository.KhataRepository
import com.downloader.roznamcha.data.repository.PersonToDealRepository
import com.downloader.roznamcha.data.repository.PurchaseHistoryRepository
import com.downloader.roznamcha.data.repository.RozNamchaRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {

    // Purchase
    single<PurchaseHistoryRepository> {
        PurchaseHistoryRepository(get())
    }
    single<RozNamchaRepository> {
        RozNamchaRepository(get())
    }

    // K
    // hata
    singleOf(::KhataRepository)
    // repositoryModule.kt
    single<BusinessRepository> {
        BusinessRepository(get())
    }
    singleOf(::EmployeeRepository)
    singleOf(::PersonToDealRepository)

}
