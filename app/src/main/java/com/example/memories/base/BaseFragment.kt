package com.example.memories.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB:ViewBinding> : Fragment()
{

    private var _binding:VB?=null

    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding = createView(inflater,container,false)

       return _binding?.root
    }


    abstract fun createView(inflater: LayoutInflater, container: ViewGroup?, b: Boolean): VB


    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }


}