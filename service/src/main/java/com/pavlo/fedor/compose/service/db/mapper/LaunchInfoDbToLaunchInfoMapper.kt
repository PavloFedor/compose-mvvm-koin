package com.pavlo.fedor.compose.service.db.mapper

import com.example.utils.mapper.Mapper
import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.LaunchStatus
import com.pavlo.fedor.compose.service.db.model.LaunchInfoDb
import com.pavlo.fedor.compose.service.db.model.LaunchStatusDb

internal class LaunchInfoDbToLaunchInfoMapper : Mapper<Unit, LaunchInfoDb, LaunchInfo> {

    override fun invoke(p1: Unit, source: LaunchInfoDb) = LaunchInfo(
        id = source.id,
        name = source.name,
        infoPage = source.infoPage,
        countryFlagLink = source.countryFlagLink,
        date = source.date,
        imageUrl = source.image,
        isFavorite = source.isFavorite,
        status = when (source.status) {
            LaunchStatusDb.SUCCEED -> LaunchStatus.SUCCEED
            LaunchStatusDb.FAILED -> LaunchStatus.FAILED
            LaunchStatusDb.IN_PROGRESS -> LaunchStatus.IN_PROGRESS
        }
    )
}
