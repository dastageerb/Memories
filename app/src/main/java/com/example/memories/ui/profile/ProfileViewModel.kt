package com.example.memories.ui.profile

import android.content.ContentValues
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memories.domain.MemoryRepo
import com.example.memories.domain.ProfileRepo
import com.example.memories.model.Memory
import com.example.memories.model.Response
import com.example.memories.model.ResponseMessage
import com.example.memories.model.User
import com.example.memories.utils.Constants.TAG
import com.example.memories.utils.stateManagement.NetworkResponse
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileViewModel(private val profileRepo: ProfileRepo):ViewModel()
{


   private val _getUser:MutableLiveData<NetworkResponse<User?>> = MutableLiveData()
   val getUser:LiveData<NetworkResponse<User?>> = _getUser


    fun getProfile() = viewModelScope.launch(Dispatchers.Main)
    {
        _getUser.value = NetworkResponse.Loading()
        try

        {
            val response = profileRepo.getProfile()
            _getUser.value = NetworkResponse.Success(response)
        }catch (e:Exception)
        {
            _getUser.value = NetworkResponse.Error(e.message)
            Log.d(TAG, "getUser: "+e.message)
        }
    } // getUser closed



    private val _uploadUserResponse:MutableSharedFlow<NetworkResponse<ResponseMessage>> = MutableSharedFlow(0)
    val uploadUserResponse:SharedFlow<NetworkResponse<ResponseMessage>> = _uploadUserResponse


    fun validateAndUploadUser(name: String,bitmap: Bitmap?,user: User?)
    {

        if (user==null)
            uploadUser(name,bitmap)

        if (user!=null)
        {
            user?.name = name

            if (bitmap==null)
            {
                uploadUser(user!!)
            }else
            {
                uploadUser(user!!, bitmap!!)
            }
        } // if closed


    }

    fun uploadUser(name:String, bitmap: Bitmap?) = viewModelScope.launch(Dispatchers.IO)
    {
        _uploadUserResponse.emit(NetworkResponse.Loading())
        try
        {
            val response = profileRepo.uploadUser(name,bitmap)
            when(response.response)
            {
                Response.SUCCESS -> _uploadUserResponse.emit(NetworkResponse.Success(response))
                Response.FAILED -> _uploadUserResponse.emit(NetworkResponse.Error(response.message))
            }
        }catch (e:Exception)
        {
            _uploadUserResponse.emit(NetworkResponse.Error(e.message))
        }
    } // closed



    fun uploadUser(user: User) = viewModelScope.launch(Dispatchers.IO)
    {
        _uploadUserResponse.emit(NetworkResponse.Loading())
        try
        {
            val response = profileRepo.uploadUser(user)
            when(response.response)
            {
                Response.SUCCESS -> _uploadUserResponse.emit(NetworkResponse.Success(response))
                Response.FAILED -> _uploadUserResponse.emit(NetworkResponse.Error(response.message))
            }
        }catch (e:Exception)
        {
            _uploadUserResponse.emit(NetworkResponse.Error(e.message))
        }
    } // closed


    fun uploadUser(user:User, bitmap: Bitmap) = viewModelScope.launch(Dispatchers.IO)
    {
        _uploadUserResponse.emit(NetworkResponse.Loading())
        try
        {
            val response = profileRepo.uploadUser(user,bitmap)
            when(response.response)
            {
                Response.SUCCESS -> _uploadUserResponse.emit(NetworkResponse.Success(response))
                Response.FAILED -> _uploadUserResponse.emit(NetworkResponse.Error(response.message))
            }
        }catch (e:Exception)
        {
            _uploadUserResponse.emit(NetworkResponse.Error(e.message))
        }
    } // closed



} // ProfileViewModel closed