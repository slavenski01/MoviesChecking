package com.example.project.shared.domain

import com.example.project.shared.base.Response
import com.example.project.shared.base.data.singleSourceOfTruth
import com.example.project.shared.cache.Database
import com.example.project.shared.entity.RocketLaunch
import com.example.project.shared.network.SpaceXApi
import kotlinx.coroutines.flow.Flow

class RocketLaunchRepoImpl(
    private val spaceXApi: SpaceXApi,
    private val database: Database
) : RocketLaunchRepo {

    private fun getLaunchesFromLocal(): List<RocketLaunch> {
        return database.getAllLaunches()
    }

    override suspend fun getLaunches(url: String): Flow<Response<List<RocketLaunch>>> =
        singleSourceOfTruth(
            getLocalData = { getLaunchesFromLocal() },
            getRemoteData = {
                spaceXApi.getAllLaunches()
            },
            saveDataToLocal = { list ->
                database.clearDatabase()
                database.createLaunches(list)
            }
        )
}