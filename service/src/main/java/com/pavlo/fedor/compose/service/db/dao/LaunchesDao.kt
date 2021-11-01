package com.pavlo.fedor.compose.service.db.dao

import androidx.room.*
import com.pavlo.fedor.compose.service.db.model.LaunchInfoDb
import com.pavlo.fedor.compose.service.db.model.LaunchesDbPage
import timber.log.Timber

@Dao
internal abstract class LaunchesDao {

    @Transaction
    open fun getLaunchesPage(query: String?, limit: Int, offset: Long): LaunchesDbPage {
        val total = countLaunches(query ?: "")
        val launches = getLaunches(limit = limit, offset = offset, query = query ?: "")
        return LaunchesDbPage(
            total = total,
            launches = launches
        )
    }

    @Transaction
    open fun getFavoriteLaunchesPage(limit: Int, offset: Long): LaunchesDbPage {
        val total = countFavorite()
        val launches = getFavoriteLaunches(limit = limit, offset = offset)
        return LaunchesDbPage(
            total = total,
            launches = launches
        )
    }

    @Query("SELECT *  FROM launch_info WHERE  is_favorite = '1' LIMIT :limit OFFSET :offset ")
    protected abstract fun getFavoriteLaunches(limit: Int, offset: Long): List<LaunchInfoDb>

    @Query("SELECT COUNT(id) FROM launch_info WHERE is_favorite = '1'")
    protected abstract fun countFavorite(): Long

    @Query("SELECT COUNT(id) FROM launch_info WHERE launch_name LIKE '%'|| :query ||'%'")
    protected abstract fun countLaunches(query: String): Long

    @Query("SELECT *  FROM launch_info WHERE  launch_name LIKE '%'|| :query ||'%' LIMIT :limit OFFSET :offset ")
    protected abstract fun getLaunches(limit: Int, offset: Long, query: String): List<LaunchInfoDb>

    @Transaction
    @Query("SELECT * FROM launch_info WHERE id in (:ids)")
    abstract fun getLaunches(ids: List<String>): List<LaunchInfoDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun save(launchesDb: LaunchInfoDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun save(launchesDb: List<LaunchInfoDb>)

    @Delete
    abstract fun delete(launchesDb: LaunchInfoDb)
}
