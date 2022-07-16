package com.example.memories.model

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser

data class Memory(
    var memoryId:String?=null
    , var description:String?=null
    , var imageUrl:String?=null
    , var timestamp:Timestamp?=null
    , var userPhoneNumber: String? =null
)
{
}
