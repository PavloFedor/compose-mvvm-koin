package com.pavlo.fedor.compose.service.db

import androidx.room.Room
import androidx.room.RoomDatabase
import com.pavlo.fedor.compose.domain.service.RocketLaunchDbService
import com.pavlo.fedor.compose.service.db.dao.LaunchesDao
import com.pavlo.fedor.compose.service.db.mapper.LaunchInfoDbToLaunchInfoMapper
import com.pavlo.fedor.compose.service.db.mapper.LaunchInfoToLaunchInfoDbMapper
import com.pavlo.fedor.compose.service.db.mapper.LaunchesDbPageToLaunchesPageResultMapper
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module

internal val LaunchesDbModule: Module.() -> Unit = {
    single { LaunchInfoDbToLaunchInfoMapper() }
    single { LaunchInfoToLaunchInfoDbMapper() }
    single { LaunchesDbPageToLaunchesPageResultMapper(launchInfoDbToLaunchInfoMapper = get()) }

    single {
        Room.databaseBuilder(androidApplication(), LaunchesDb::class.java, LaunchesDb.NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<LaunchesDb>().getLaunchesDao() }

    single<RocketLaunchDbService> {
        RocketLaunchDbServiceImpl(
            launchesDao = get(),
            launchInfoDbToLaunchInfoMapper = get(),
            launchesDbPageToLaunchesPageResultMapper = get(),
            launchInfoToLaunchInfoDbMapper = get()
        )
    }
}