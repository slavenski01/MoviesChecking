package com.example.project.shared.domain.usecases

import com.example.project.shared.base.Response
import com.example.project.shared.domain.RocketLaunchRepo
import com.example.project.shared.entity.RocketLaunch
import kotlinx.coroutines.flow.Flow

class RocketLaunchesUseCase(
    private val rocketLaunchRepo: RocketLaunchRepo
) {
    suspend fun getAllLaunches(url: String): Flow<Response<List<RocketLaunch>>> {
        return rocketLaunchRepo.getLaunches(url)
    }
}