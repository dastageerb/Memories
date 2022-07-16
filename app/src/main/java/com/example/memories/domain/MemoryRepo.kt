package com.example.memories.domain

import android.graphics.Bitmap
import com.example.memories.model.Memory
import com.example.memories.model.ResponseMessage
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot


interface MemoryRepo
{

    suspend fun getAllMemories(): List<Memory>


    suspend fun addMemory(description:String,bitmap: Bitmap): ResponseMessage

    suspend fun deleteMemory(id:String): ResponseMessage

    suspend fun updateMemory(memory: Memory): ResponseMessage

  //  suspend fun getMemoryById(id: String): Memory


}