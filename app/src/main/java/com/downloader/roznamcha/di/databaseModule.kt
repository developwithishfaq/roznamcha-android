package com.downloader.roznamcha.di

import com.downloader.roznamcha.data.db.AppDatabase
import org.koin.dsl.module

val databaseModule = module {

    single {
        AppDatabase.getInstance(get())
    }
    single { get<AppDatabase>().businessDao() }
    single { get<AppDatabase>().employeeDao() }
    single { get<AppDatabase>().khataDao() }
    single { get<AppDatabase>().rozNamchaPaymentDao() }
    single { get<AppDatabase>().personToDealDao() }
    single { get<AppDatabase>().purchaseHistoryDao() }
}
