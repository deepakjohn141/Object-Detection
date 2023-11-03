package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.instrumentedTestModule
import org.koin.core.context.startKoin

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(instrumentedTestModule)
        }
    }
}