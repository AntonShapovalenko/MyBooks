package com.books.app.data.model

sealed class DataResult<out T> {
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Failure(val exception: Throwable) : DataResult<Nothing>()
    data object Loading : DataResult<Nothing>()
}