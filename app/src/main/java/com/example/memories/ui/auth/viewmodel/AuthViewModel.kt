package com.example.memories.ui.auth.viewmodel

import android.app.Activity
import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memories.domain.AuthRepo
import com.example.memories.utils.Constants.TAG
import com.example.memories.utils.stateManagement.NetworkResponse
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.internal.resumeCancellableWith
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthViewModel(private  val authRepo: AuthRepo,private val firebaseAuth: FirebaseAuth) : ViewModel()
{


    private val _response:MutableSharedFlow<NetworkResponse<Pair<Boolean,String>>> = MutableSharedFlow(1)

    val response:SharedFlow<NetworkResponse<Pair<Boolean,String>>> = _response


    fun currentUser() = firebaseAuth.currentUser


    fun sendOtp(number: String,activity: Activity) = viewModelScope.launch(Dispatchers.Main)
    {
        _response.emit(NetworkResponse.Loading())
        try
        {
            val response = sendOtpCallbackManager(number,activity)

            if (response.first)
                _response.emit(NetworkResponse.Success(response))
            else
                _response.emit(NetworkResponse.Error(response.second))

        }catch (e:Exception)
        {
            _response.emit(NetworkResponse.Error(e.message.toString()))
        }
    }



    private suspend fun sendOtpCallbackManager(number: String, activity: Activity) = suspendCoroutine<Pair<Boolean,String>>
    {
        cont ->
        val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks()
        {
            override fun onCodeSent(verificationId: String, p1: PhoneAuthProvider.ForceResendingToken)
            {
                super.onCodeSent(verificationId, p1)
                cont.resume(Pair(true,verificationId))
            }
            override fun onVerificationCompleted(p0: PhoneAuthCredential)
            {

            }

            override fun onVerificationFailed(exception: FirebaseException)
            {
                cont.resumeWithException(Throwable(exception.message.toString()))
            }
        }

        var otpNumber = "+92"+number.substringAfter("0")
        Log.d(TAG, "sendOtpCallbackManager: "+otpNumber)

        val options = PhoneAuthOptions.newBuilder(firebaseAuth!!)
            .setPhoneNumber(otpNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setCallbacks(callback)
            .setActivity(activity)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }



    // verify PinCode
    fun verifyOtp(verificationCode: String,otp:String)
    {


    }




    /// validations

    fun isNumberValid(number: String): Pair<Boolean,String>
    {
        return if (number.isNotEmpty() && number.isDigitsOnly() && number.length == 11)
            Pair(true,"")
        else
            Pair(false,"Please enter a valid number")
    }


    fun validatePinCode(pinCode: String): Pair<Boolean,String>
    {
        return if (pinCode.isNotEmpty() && pinCode.isDigitsOnly())
            Pair(true,"")
        else
            Pair(false,"Invalid Input")
    }

    fun validateRegisterEntities(name: String, phone: String): Pair<Boolean,String>
    {
        if (name.isNotEmpty())
            return Pair(false,"Please Enter Name")
        return isNumberValid(phone)
    }

    // sharedEntity

    var verificationId:String?=null


}