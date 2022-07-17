package com.example.memories.domain

import android.app.Activity
import com.example.memories.model.ResponseMessage
import com.example.memories.model.User

interface AuthRepo
{

    suspend fun sendOtp(number: String, activity: Activity):Pair<Boolean,String>

    suspend fun verifyOtp(verificationId:String,otpCode:String):Pair<Boolean,String>

}