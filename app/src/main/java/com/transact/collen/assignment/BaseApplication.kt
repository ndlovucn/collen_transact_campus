package com.transact.collen.assignment

import android.app.Application
import com.transact.collen.assignment.di.repositoryModule
import com.transact.collen.assignment.di.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(listOf(repositoryModule, databaseModule))
        }
    }
}