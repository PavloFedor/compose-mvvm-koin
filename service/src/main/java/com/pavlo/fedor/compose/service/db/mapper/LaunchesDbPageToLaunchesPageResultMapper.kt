package com.pavlo.fedor.compose.service.db.mapper

import com.example.utils.mapper.Mapper
import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.PageRequest
import com.pavlo.fedor.compose.domain.model.PageResult
import com.pavlo.fedor.compose.service.db.model.LaunchesDbPage

internal class LaunchesDbPageToLaunchesPageResultMapper(
    private val launchInfoDbToLaunchInfoMapper: LaunchInfoDbToLaunchInfoMapper
) : Mapper<PageRequest, LaunchesDbPage, PageResult<LaunchInfo>> {
    override fun invoke(pageRequest: PageRequest, dbPage: LaunchesDbPage) = PageResult(
        offset = pageRequest.offset,
        total = dbPage.total,
        entities = dbPage.launches.map { launchInfoDbToLaunchInfoMapper(Unit, it) }
    )
}