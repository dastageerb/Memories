package com.example.memories.utils.extensionFunctions

import android.view.View


object ExtensionFunctions
{


    fun View.show()
    {
            this.visibility = View.VISIBLE
    }

    fun View.hide()
    {
            this.visibility = View.GONE
    }


    fun View.inVisible()
    {
            this.visibility = View.INVISIBLE
    }

    fun View.enable()
    {
            this.isEnabled = true
    }

    fun View.disable()
    {
            this.isEnabled = false
    }






}