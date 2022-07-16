package com.example.memories.di

import com.example.memories.data.remote.AuthRepoImpl
import com.example.memories.data.remote.MemoryRepoImpl
import com.example.memories.data.remote.StorageRepoImpl
import com.example.memories.data.repository.StorageRepo
import com.example.memories.domain.AuthRepo
import com.example.memories.domain.MemoryRepo
import com.example.memories.domain.StoryRepo
import com.example.memories.ui.auth.viewmodel.AuthViewModel
import com.example.memories.ui.memory.MemoryViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val repoModule = module{


    single<MemoryRepo> {
        MemoryRepoImpl(get(),get(),get())
    }

    single <StorageRepo> {
        StorageRepoImpl(get())
    }

    single { MemoryViewModel(get()) }

    single <AuthRepo>{ AuthRepoImpl(get()) }

    single <AuthViewModel>{  AuthViewModel(get(),get()) }





}

