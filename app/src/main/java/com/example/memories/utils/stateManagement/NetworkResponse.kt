package com.example.memories.utils.stateManagement

sealed class NetworkResponse<T>(private val data:T?=null, private val msg:String?=null)
{

    class Success<T>(val data:T?) : NetworkResponse<T>(data)
    class Error<T>(val msg: String?): NetworkResponse<T>(null,msg)
    class Loading<T>: NetworkResponse<T>()
    class None<T>: NetworkResponse<T>()

}