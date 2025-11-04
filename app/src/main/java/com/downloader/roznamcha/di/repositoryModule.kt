package com.downloader.roznamcha.di

import com.downloader.roznamcha.data.repository.BusinessRepository
import com.downloader.roznamcha.data.repository.KhataRepository
import com.downloader.roznamcha.data.repository.PurchaseHistoryRepository
import com.downloader.roznamcha.data.repository.RozNamchaRepository
import com.downloader.roznamcha.presentation.business.BusinessViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val repositoryModule = module {

    // Purchase
    single<PurchaseHistoryRepository> {
        PurchaseHistoryRepository(get())
    }
    single<RozNamchaRepository> {
        RozNamchaRepository(get())
    }

    // Khata
    single<KhataRepository> {
        KhataRepository(get())
    }
    // repositoryModule.kt
    single<BusinessRepository> {
        BusinessRepository(get())
    }

// viewModelModule.kt
    viewModelOf(::BusinessViewModel)

}
