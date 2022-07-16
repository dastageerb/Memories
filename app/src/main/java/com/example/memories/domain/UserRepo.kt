package com.example.memories.domain

import android.graphics.Bitmap
import com.google.firebase.storage.UploadTask

interface UserRepo
{

    suspend fun registerUser(name:String,number:String)

    suspend fun uploadUserImage(bitmap: Bitmap)


}