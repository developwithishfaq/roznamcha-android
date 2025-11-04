package com.downloader.roznamcha.app

import android.app.Application
import com.downloader.roznamcha.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppClass : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(allModules)
        }
    }
}