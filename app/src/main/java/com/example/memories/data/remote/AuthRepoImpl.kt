package com.example.memories.data.remote

import android.app.Activity
import com.example.memories.domain.AuthRepo
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class AuthRepoImpl(private val firebaseAuth: FirebaseAuth) : AuthRepo
{


    override suspend fun sendOtp(number: String, activity: Activity) = suspendCoroutine<Pair<Boolean,String>>
    {
            cont ->
        val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks()
        {
            override fun onCodeSent(code: String, p1: PhoneAuthProvider.ForceResendingToken)
            {
                super.onCodeSent(code, p1)
                cont.resume(Pair(true,code))
            }
            override fun onVerificationCompleted(p0: PhoneAuthCredential)
            {

            }

            override fun onVerificationFailed(exception: FirebaseException)
            {
                cont.resumeWithException(Throwable(exception.message.toString()))
            }
        }
        val options = PhoneAuthOptions.newBuilder(firebaseAuth!!)
            .setPhoneNumber(number)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setCallbacks(callback)
            .setActivity(activity)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    override suspend fun verifyOtp()
    {
        TODO("Not yet implemented")
    }


}