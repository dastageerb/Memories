package com.example.memories.domain

import android.annotation.SuppressLint
import android.app.Activity
import com.example.memories.model.ContactEntity
import com.example.memories.model.ResponseMessage
import com.example.memories.model.User

interface ContactsRepo
{


    fun getContactNumbers(): List<ContactEntity>



} /// ContactsRepo closed