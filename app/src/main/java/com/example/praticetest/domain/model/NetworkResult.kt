package com.example.praticetest.domain.model

sealed class NetworkResult<out T> {


    data class Success<out T>(val data: T) : NetworkResult<T>()

    data class Error(val message: String) : NetworkResult<Nothing>()

}

