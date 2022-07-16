package com.example.memories.domain
import com.example.memories.model.Story

interface StoryRepo
{

    suspend fun getAllStories():List<Story>

    suspend fun addStory(story: Story)

    suspend fun deleteStory(id:String)





}