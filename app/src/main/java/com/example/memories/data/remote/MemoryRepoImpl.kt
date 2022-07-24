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
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
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


        Log.d(TAG, "getAllMemories: "+firebaseAuth.currentUser!!.phoneNumber)
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
        {
            emptyList()
        }
    } // getAllMemories closed

    override suspend fun addMemory(description:String,bitmap: Bitmap): ResponseMessage
    {
        if (firebaseAuth.currentUser==null)
            return ResponseMessage(Response.FAILED,"unauthorized user")

        val id = memoryCollection.document().id
        val link =  storageRepo.uploadImage(bitmap,Constants.MEMORY_STORAGE)
        val memory = Memory(id,description,link, timestamp = Timestamp.now(), firebaseAuth.currentUser!!.phoneNumber)

         val task = memoryCollection.add(memory)
        task.await()

        return if (task.isSuccessful)
            ResponseMessage(Response.SUCCESS,"Added Successfully")
        else
            ResponseMessage(Response.FAILED,""+task.exception?.message.toString())

    } // addMemory closed


    override suspend fun deleteMemory(id: String): ResponseMessage
    {

        return ResponseMessage(Response.SUCCESS,"")
        // return memoryCollection.document(id).delete()
    } // deleteMemory


    override suspend fun updateMemory(memory: Memory): ResponseMessage
    {
        return ResponseMessage(Response.SUCCESS,"")
       // return memoryCollection.document(memory.memoryId.toString()).set(memory)
    } // updateMemory


//
//    override suspend fun getMemoryById(id: String): Memory
//    {
//
//        return memoryCollection.document(id).get()
//    } // getMemoryById closed


} // MemoryImpl