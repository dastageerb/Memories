package com.example.memories.base

import android.app.Application
import com.example.memories.di.firebaseModule
import com.example.memories.di.repoModule
import com.example.memories.di.systemModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class BaseApp  : Application()
{

    override fun onCreate()
    {
        super.onCreate()

        startKoin()
        {
            androidContext(this@BaseApp)
            modules(listOf(repoModule, firebaseModule, systemModule))
        }

    }

}