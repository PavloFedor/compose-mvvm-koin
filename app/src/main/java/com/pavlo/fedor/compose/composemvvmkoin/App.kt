package com.pavlo.fedor.compose.composemvvmkoin

import android.app.Application
import com.pavlo.fedor.compose.domain.DomainModule
import com.pavlo.fedor.compose.flow.FLowModule
import com.pavlo.fedor.compose.service.ServiceModule
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            modules(listOf(FLowModule, DomainModule, ServiceModule))
        }
    }
}
