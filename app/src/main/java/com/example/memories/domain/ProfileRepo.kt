package com.example.memories.domain

import android.graphics.Bitmap
import com.example.memories.model.ResponseMessage
import com.example.memories.model.User

interface ProfileRepo
{

    suspend fun uploadUser(name:String,bitmap: Bitmap):ResponseMessage

    suspend fun uploadUser(user: User):ResponseMessage

    suspend fun getProfile():User?

}