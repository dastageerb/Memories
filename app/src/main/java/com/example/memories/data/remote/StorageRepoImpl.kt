package com.example.memories.data.remote

import android.graphics.Bitmap
import com.example.memories.data.repository.StorageRepo
import com.example.memories.utils.Constants
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream


class StorageRepoImpl(private val storageReference: StorageReference) : StorageRepo
{


    override suspend fun uploadImage(bitmap: Bitmap,collection:String): String
    {
        val storage = storageReference.child(collection)
        val child =  storage.child("image"+System.currentTimeMillis()+".jpg")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        val data: ByteArray = baos.toByteArray()
        val task  =  child.putBytes(data).await().storage.downloadUrl
        task.await()

        return if (task.isSuccessful)
        {
            task.result.toString()
        }else ""

    } // uploadImage closed









}