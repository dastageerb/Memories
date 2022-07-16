package com.example.memories.di

import com.example.memories.data.remote.MemoryRepoImpl
import com.example.memories.domain.MemoryRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import org.koin.core.module.Module
import org.koin.dsl.module

val firebaseModule = module{


    single {
        FirebaseFirestore.getInstance()
    }

    single {
         FirebaseStorage.getInstance().reference
    }

    single{
        FirebaseAuth.getInstance()
    }






}

