package com.pavlo.fedor.compose.service.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pavlo.fedor.compose.service.db.dao.LaunchesDao
import com.pavlo.fedor.compose.service.db.model.LaunchInfoDb
import com.pavlo.fedor.compose.service.db.model.LaunchStatusDBConverter

@Database(
    version = 2,
    exportSchema = false,
    entities = [
        LaunchInfoDb::class
    ],
)
@TypeConverters(
    LaunchStatusDBConverter::class
)
internal abstract class LaunchesDb : RoomDatabase() {

    companion object {
        const val NAME = "launches_db"
    }

    abstract fun getLaunchesDao(): LaunchesDao
}
