package com.example.memories.di

import com.example.memories.data.remote.AuthRepoImpl
import com.example.memories.data.remote.MemoryRepoImpl
import com.example.memories.data.remote.ProfileRepoImpl
import com.example.memories.data.remote.StorageRepoImpl
import com.example.memories.data.repository.StorageRepo
import com.example.memories.domain.AuthRepo
import com.example.memories.domain.MemoryRepo
import com.example.memories.domain.ProfileRepo
import com.example.memories.ui.auth.AuthViewModel
import com.example.memories.ui.memory.MemoryViewModel
import com.example.memories.ui.profile.ProfileViewModel
import org.koin.dsl.module

val repoModule = module{


    // storage repo
    single <StorageRepo> {
        StorageRepoImpl(get())
    }




    single<MemoryRepo> {
        MemoryRepoImpl(get(),get(),get())
    }

    single { MemoryViewModel(get()) }



    single <AuthRepo>{ AuthRepoImpl(get()) }

    single {  AuthViewModel(get(),get()) }


    single <ProfileRepo>{ ProfileRepoImpl(get(),get(),get()) }

    single { ProfileViewModel(get()) }




}

