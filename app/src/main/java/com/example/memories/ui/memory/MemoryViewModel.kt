package com.example.memories.ui.memory

import android.content.ContentValues
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memories.domain.MemoryRepo
import com.example.memories.model.Memory
import com.example.memories.model.Response
import com.example.memories.model.ResponseMessage
import com.example.memories.utils.stateManagement.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MemoryViewModel(private val memoryRepo: MemoryRepo):ViewModel()
{


    private val _allMemoriesResponse : MutableLiveData<NetworkResponse<List<Memory>>> = MutableLiveData();
    val allMemoriesResponse:LiveData<NetworkResponse<List<Memory>>> =_allMemoriesResponse


    fun getAllMemories() = viewModelScope.launch(Dispatchers.Main)
    {

       _allMemoriesResponse.value = NetworkResponse.Loading()
            try
            {
                val result = memoryRepo.getAllMemories()
                _allMemoriesResponse.value = NetworkResponse.Success(result)
            }
            catch (e:Exception)
            {
                _allMemoriesResponse.value = NetworkResponse.Error(e.message)
                Log.d(ContentValues.TAG, "getAllMemories: "+e.message)
            } // catch closed
    } // getAllMemories closed



    fun getMemoryById()
    {

    }

    private val _memoryResponse:MutableSharedFlow<NetworkResponse<ResponseMessage>>  = MutableSharedFlow(0)
    val memoryResponse:SharedFlow<NetworkResponse<ResponseMessage>> = _memoryResponse


    fun addMemory(description: String,bitmap: Bitmap)  = viewModelScope.launch(Dispatchers.IO)
    {
        _memoryResponse.emit(NetworkResponse.Loading())
        try
        {
            val result = memoryRepo.addMemory(description,bitmap)

            when(result.response)
            {
                Response.SUCCESS -> _memoryResponse.emit(NetworkResponse.Success(result))
                else -> _memoryResponse.emit(NetworkResponse.Error(result.message))
            }

        }catch (e:Exception)
        {
            _memoryResponse.emit(NetworkResponse.Error(e.message.toString()))
        }


    } // addMemory closed

    fun updateMemory(memory: Memory) = viewModelScope.launch(Dispatchers.IO)
    {

    }


    fun deleteMemory(id: String) = viewModelScope.launch(Dispatchers.IO)
    {
        _memoryResponse.emit(NetworkResponse.Loading())
        try
        {
            val result = memoryRepo.deleteMemory(id)
            when(result.response)
            {
                Response.SUCCESS -> _memoryResponse.emit(NetworkResponse.Success(result))
                else -> _memoryResponse.emit(NetworkResponse.Error(result.message))
            }
        }catch (e:Exception)
        {
            _memoryResponse.emit(NetworkResponse.Error(e.message.toString()))
        }
    }





    /// Validations


    fun validateMemory(description: String, bitmap: Bitmap?): Pair<Boolean,String>
    {
        if (description.isEmpty())
            return Pair(false,"Please Enter Description")
        if (bitmap == null)
            return Pair(false,"Please Insert Image")

        return Pair(true,"")
    }



    /// shared Entity

    var memory:Memory?=null


}