package com.example.memories.data.remote

import android.graphics.Bitmap
import android.util.Log
import com.example.memories.data.repository.StorageRepo
import com.example.memories.domain.MemoryRepo
import com.example.memories.model.Memory
import com.example.memories.model.Response
import com.example.memories.model.ResponseMessage
import com.example.memories.utils.Constants
import com.example.memories.utils.Constants.TAG
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class MemoryRepoImpl(
    fireStore: FirebaseFirestore
    ,private val storageRepo: StorageRepo
    ,private val firebaseAuth: FirebaseAuth
    ) : MemoryRepo
{


    private val memoryCollection = fireStore.collection(Constants.MEMORY_COLLECTION)

    override suspend fun getAllMemories(): List<Memory>
    {
        if (firebaseAuth.currentUser==null)
            return emptyList()

        val list = mutableListOf<Memory>()
        val task =  memoryCollection
            .whereEqualTo("userPhoneNumber",firebaseAuth.currentUser!!.phoneNumber)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
        task.await()
        return if (task.isSuccessful)
        {
            task.result?.forEach()
            {
                list.add(it.toObject(Memory::class.java))
            }
            list
        }else
            emptyList()

    } // getAllMemories closed

    override suspend fun addMemory(description:String,bitmap: Bitmap): ResponseMessage
    {
        if (firebaseAuth.currentUser==null)
            return ResponseMessage(Response.FAILED,"unauthorized user")

        val id = memoryCollection.document().id
        val link =  storageRepo.uploadImage(bitmap,Constants.MEMORY_STORAGE)
        val memory = Memory(id,description,link, timestamp = Timestamp.now(), firebaseAuth.currentUser!!.phoneNumber)

         val task = memoryCollection.document(id).set(memory)
        task.await()

        return taskResult(task,"Added")

    } // addMemory closed


    override suspend fun deleteMemory(id: String): ResponseMessage
    {
        val task = memoryCollection.document(id).delete()
        task.await()
        return taskResult(task,"Deleted")
    } // deleteMemory




    override suspend fun updateMemory(memory: Memory): ResponseMessage
    {
        if (memory.memoryId ==null)
            return ResponseMessage(Response.FAILED,"update failed")
        val task = memoryCollection.document(memory.memoryId!!).set(memory)
        task.await()

        return taskResult(task,"Updated")
    } // updateMemory



    private fun <T> taskResult(task: Task<T>, message: String): ResponseMessage
    {
        return if (task.isSuccessful)
            ResponseMessage(Response.SUCCESS,"$message Successfully")
        else
            ResponseMessage(Response.FAILED,""+task.exception?.message.toString())
    }



//    override suspend fun getMemoryById(id: String): Memory
//    {
//
//        return memoryCollection.document(id).get()
//    } // getMemoryById closed


} // MemoryImpl