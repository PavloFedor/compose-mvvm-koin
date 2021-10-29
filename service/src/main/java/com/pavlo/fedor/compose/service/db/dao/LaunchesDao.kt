package com.pavlo.fedor.compose.service.db.dao

import androidx.room.*
import com.pavlo.fedor.compose.service.db.model.LaunchInfoDb
import com.pavlo.fedor.compose.service.db.model.LaunchesDbPage

@Dao
internal abstract class LaunchesDao {

    @Transaction
    open fun getLaunchesPage(limit: Int, offset: Long): LaunchesDbPage {
        val total = countLaunches()
        val launches = getLaunches(limit = limit, offset = offset)
        return LaunchesDbPage(
            total = total,
            launches = launches
        )
    }

    @Query("SELECT COUNT(id) FROM launch_info")
    protected abstract fun countLaunches(): Long

    @Query("SELECT *  FROM launch_info LIMIT :limit OFFSET :offset")
    protected abstract fun getLaunches(limit: Int, offset: Long): List<LaunchInfoDb>

    @Transaction
    @Query("SELECT * FROM launch_info WHERE id in (:ids)")
    abstract fun getLaunches(ids: List<String>): List<LaunchInfoDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun save(launchesDb: LaunchInfoDb)

    @Delete
    abstract fun delete(launchesDb: LaunchInfoDb)
}
