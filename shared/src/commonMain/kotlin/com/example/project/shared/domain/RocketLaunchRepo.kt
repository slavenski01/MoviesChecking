package com.example.project.shared.domain

import com.example.project.shared.base.Response
import com.example.project.shared.entity.RocketLaunch
import kotlinx.coroutines.flow.Flow

interface RocketLaunchRepo {
    suspend fun getLaunches(url: String): Flow<Response<List<RocketLaunch>>>
}