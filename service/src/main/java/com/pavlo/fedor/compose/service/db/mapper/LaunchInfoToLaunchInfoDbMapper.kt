package com.pavlo.fedor.compose.service.db.mapper

import com.example.utils.mapper.Mapper
import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.LaunchStatus
import com.pavlo.fedor.compose.service.db.model.LaunchInfoDb
import com.pavlo.fedor.compose.service.db.model.LaunchStatusDb

internal class LaunchInfoToLaunchInfoDbMapper : Mapper<Unit, LaunchInfo, LaunchInfoDb> {

    override fun invoke(p1: Unit, source: LaunchInfo) = LaunchInfoDb(
        id = source.id,
        name = source.name,
        infoPage = source.infoPage,
        countryFlagLink = source.countryFlagLink,
        date = source.date,
        image = source.imageUrl,
        status = when (source.status) {
            LaunchStatus.SUCCEED -> LaunchStatusDb.SUCCEED
            LaunchStatus.FAILED -> LaunchStatusDb.FAILED
            LaunchStatus.IN_PROGRESS -> LaunchStatusDb.IN_PROGRESS
        }
    )
}