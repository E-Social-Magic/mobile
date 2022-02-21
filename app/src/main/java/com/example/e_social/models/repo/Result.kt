package com.example.e_social.models.repo

sealed class Result<out T>(
    val data:T?=null,
    val message:String?=null
){

    class Success< out T>(data: T?): Result<T>(data)
    class Error<T>(message: String?,data: T?=   null) : Result<T>(data,message)
    class Loading<T>(data:  T?): Result<T>(data)
}
