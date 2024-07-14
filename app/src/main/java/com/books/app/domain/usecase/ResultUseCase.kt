package com.books.app.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class ResultUseCase<in P, R> {

    operator fun invoke(params: P? = null): Flow<R> = flow {
        emit(doWork(params))
    }

    suspend fun executeSync(params: P? = null): R = doWork(params)

    protected abstract suspend fun doWork(params: P?): R
}

fun <R> ResultUseCase<Any?, R>.invoke(): Flow<R> {
    return this.invoke(null)
}