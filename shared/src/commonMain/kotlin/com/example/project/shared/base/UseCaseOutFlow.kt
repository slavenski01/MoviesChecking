package com.example.project.shared.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

abstract class UseCaseOutFlow<IN, OUT> {
    operator fun invoke(param: IN): Flow<Response<OUT>> = flow {
        try {
            block(param).map { Response.Success(data = it) }
        } catch (ex: Exception) {
            flowOf(
                Response.Error(
                    errorMessage = ex.message.toString(),
                    throwable = ex.cause ?: Throwable()
                )
            )
        }
    }

    protected abstract val block: suspend (param: IN) -> Flow<OUT>
}