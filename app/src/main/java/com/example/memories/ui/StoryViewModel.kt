package com.example.memories.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memories.domain.StoryRepo
import com.example.memories.model.Story
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StoryViewModel(val repo: StoryRepo) : ViewModel()
{


    fun addStory(story: Story) = viewModelScope.launch(Dispatchers.IO)
    {
        repo.addStory(story)
    }

    fun deleteStory(id:String) =viewModelScope.launch(Dispatchers.IO)
    {
        repo.deleteStory(id)
    }




}