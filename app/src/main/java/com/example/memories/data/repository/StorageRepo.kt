package com.example.memories.data.repository

import android.graphics.Bitmap
import com.google.firebase.storage.UploadTask

interface StorageRepo
{

    suspend fun uploadImage(bitmap: Bitmap,collection:String): String



}