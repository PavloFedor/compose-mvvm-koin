package com.pavlo.fedor.compose.composemvvmkoin

import android.app.Application
import com.pavlo.fedor.compose.flow.FLowModule
import org.koin.core.context.startKoin

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(FLowModule))
        }
    }
}
