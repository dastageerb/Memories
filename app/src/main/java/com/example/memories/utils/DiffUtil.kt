package com.example.memories.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.memories.model.Memory

class DiffUtilCallback<T> : DiffUtil.ItemCallback<T>()
{
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean
    {
        return oldItem == newItem
    }


    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean
    {
        return oldItem.toString() == newItem.toString()
    }
}