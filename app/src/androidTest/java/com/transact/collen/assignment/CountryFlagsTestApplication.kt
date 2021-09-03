package com.transact.collen.assignment

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class CountryFlagsTestApplication : Application()  {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(androidContext = this@CountryFlagsTestApplication)

        }
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }
}

class TestAppJUnitRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, CountryFlagsTestApplication::class.java.name, context)
    }
}