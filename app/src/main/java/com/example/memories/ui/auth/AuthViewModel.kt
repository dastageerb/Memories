package com.example.memories.ui.auth

import android.app.Activity
import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memories.domain.AuthRepo
import com.example.memories.model.Response
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
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthViewModel(private  val authRepo: AuthRepo,private val firebaseAuth: FirebaseAuth) : ViewModel()
{


    private val _response:MutableSharedFlow<NetworkResponse<Pair<Boolean,String>>> = MutableSharedFlow(0)

    val response:SharedFlow<NetworkResponse<Pair<Boolean,String>>> = _response


    fun currentUser() = firebaseAuth.currentUser


    fun sendOtp(number: String,activity: Activity) = viewModelScope.launch(Dispatchers.Main)
    {
        _response.emit(NetworkResponse.Loading())
        try
        {
            val response = authRepo.sendOtp(number,activity)

            if (response.first)
                _response.emit(NetworkResponse.Success(response))
            else
                _response.emit(NetworkResponse.Error(response.second))

        }catch (e:Exception)
        {
            _response.emit(NetworkResponse.Error(e.message.toString()))
        }
    }






    // verify PinCode
    fun verifyOtp(otp: String) = viewModelScope.launch(Dispatchers.IO)
    {
        if (verificationId == null)
        {
            _response.emit(NetworkResponse.Error("Something went wrong resend otp"))
            return@launch
        }
        _response.emit(NetworkResponse.Loading())

        try
        {
            val response = authRepo.verifyOtp(verificationId!!,otp)
            if(response.first)
                _response.emit(NetworkResponse.Success(response))
            else
                _response.emit(NetworkResponse.Error(response.second.toString()))
        }catch (e:Exception)
        {
            _response.emit(NetworkResponse.Error(e.message.toString()))
        } // catch closed
    }




    /// validations

    fun isNumberValid(number: String): Pair<Boolean,String>
    {
        return if (number.isNotEmpty() && number.isDigitsOnly() && number.length == 10)
            Pair(true,"")
        else
            Pair(false,"Please enter a valid number")
    } // isNumberValid closed


    fun validatePinCode(pinCode: String): Pair<Boolean,String>
    {
        return if (pinCode.isNotEmpty() && pinCode.isDigitsOnly())
            Pair(true,"")
        else
            Pair(false,"Invalid Input")
    } // validate PinCode closed


    fun validateRegisterEntities(name: String, phone: String): Pair<Boolean,String>
    {
        if (name.isNotEmpty())
            return Pair(false,"Please Enter Name")
        return isNumberValid(phone)
    } // validateRegisterEntities closed

    // sharedEntity

    var verificationId:String?=null


}