package com.downloader.roznamcha.di

import com.downloader.roznamcha.core.prefs.PreferencesHelper
import org.koin.dsl.module

val storageModule = module {
    single { PreferencesHelper(get()) }
}
