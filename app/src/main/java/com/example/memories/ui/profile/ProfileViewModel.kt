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
import com.example.memories.utils.Constants.TAG
import com.example.memories.utils.stateManagement.NetworkResponse
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.auth.User
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


    fun getProfile() = viewModelScope.launch(Dispatchers.IO)
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

    private val _userNameResponse:MutableSharedFlow<NetworkResponse<ResponseMessage>> = MutableSharedFlow(0)
    val userNameResponse:SharedFlow<NetworkResponse<ResponseMessage>> = _userNameResponse


    fun setUserName(name:String) = viewModelScope.launch(Dispatchers.IO)
    {
        _userNameResponse.emit(NetworkResponse.Loading())
        try
        {
            val response = profileRepo.setUserName(name)
            when(response.response)
            {
                Response.SUCCESS -> _userNameResponse.emit(NetworkResponse.Success(response))
                Response.FAILED -> _userNameResponse.emit(NetworkResponse.Error(response.message))
            }
        }catch (e:Exception)
        {
            _userNameResponse.emit(NetworkResponse.Error(e.message))
        }

    } // setUserName closed


    fun setUserProfile(bitmap: Bitmap) = viewModelScope.launch(Dispatchers.IO)
    {
        profileRepo.setUserImage(bitmap)
    } // setUserName closed


}