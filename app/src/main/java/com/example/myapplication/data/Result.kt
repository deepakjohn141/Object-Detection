package com.example.myapplication.data

sealed class Result<T>(message: String? = null) {
     class Success<T>(val message: String): Result<T>(message)
     class Error<T>(val message: String): Result<T>(message)
}