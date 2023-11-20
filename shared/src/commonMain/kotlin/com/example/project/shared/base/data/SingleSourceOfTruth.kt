package com.example.project.shared.base.data

import com.example.project.shared.base.Response
import com.example.project.shared.base.getResponse
import com.example.project.shared.entity.RocketLaunch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal fun singleSourceOfTruth(
    getLocalData: suspend () -> List<RocketLaunch>,
    getRemoteData: suspend () -> List<RocketLaunch>,
    saveDataToLocal: suspend (List<RocketLaunch>) -> Unit
): Flow<Response<List<RocketLaunch>>> = flow {
    val localData = getResponse { getLocalData() }

    if (localData is Response.Success && localData.data.isNotEmpty()) {
        emit(localData)
    } else {
        val remoteData = getResponse { getRemoteData() }

        if (remoteData is Response.Success) {
            if (remoteData.data.isNotEmpty()) {
                saveDataToLocal(remoteData.data)
                val localDataUpdated = getResponse { getLocalData() }
                emit(localDataUpdated)
            }
        } else {
            emit(Response.Error("Error", (remoteData as Response.Error).throwable))
        }
    }
}