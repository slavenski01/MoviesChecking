package com.example.project.shared.base

sealed class Response<out R> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val errorMessage: String, val throwable: Throwable) : Response<Nothing>()
}