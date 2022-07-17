package com.example.memories.data.remote

import android.app.Activity
import android.graphics.Bitmap
import com.example.memories.data.repository.StorageRepo
import com.example.memories.domain.ProfileRepo
import com.example.memories.model.Response
import com.example.memories.model.ResponseMessage
import com.example.memories.model.User
import com.example.memories.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class ProfileRepoImpl(
    fireStore: FirebaseFirestore
    ,private val storageRepo: StorageRepo
    ,private val firebaseAuth: FirebaseAuth):ProfileRepo
{


    private val userCollection = fireStore.collection(Constants.USER_COLLECTION)

    override suspend fun uploadUser(name: String, bitmap: Bitmap): ResponseMessage
    {
        if (firebaseAuth.currentUser==null)
            return ResponseMessage(Response.FAILED,"unauthorized user")

        val id = userCollection.document().id
        val link =  storageRepo.uploadImage(bitmap,Constants.PROFILE_STORAGE)

        val user = User(id,name,firebaseAuth.currentUser!!.phoneNumber,link)

        return uploadUser(user)

    }

    override suspend fun uploadUser(user: User):ResponseMessage
    {
        val task = userCollection.document(user.number!!).set(user)
        task.await()

        return if (task.isSuccessful)
            ResponseMessage(Response.SUCCESS,"Added Successfully")
        else
            ResponseMessage(Response.FAILED,""+task.exception?.message.toString())

    } // uploadUser closed

    override suspend fun getProfile(): User?
    {
        if (firebaseAuth.currentUser==null)
            return null

        val task = userCollection
            .document(firebaseAuth.currentUser!!.phoneNumber!!)
            .get()
        task.await()

        return task.result.toObject(User::class.java)

    }


}