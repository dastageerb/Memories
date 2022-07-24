package com.example.memories.ui.memory

import MemoryAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memories.R
import com.example.memories.base.BaseFragment
import com.example.memories.databinding.FragmentHomeBinding
import com.example.memories.model.Memory
import com.example.memories.utils.Constants.TAG
import com.example.memories.utils.extensionFunctions.ExtensionFunctions.hide
import com.example.memories.utils.extensionFunctions.ExtensionFunctions.show
import com.example.memories.utils.stateManagement.NetworkResponse
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>() , View.OnClickListener
{ // home fragment closed

    private val memoryViewModel:MemoryViewModel by sharedViewModel()
    var adapter:MemoryAdapter?=null

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, b: Boolean): FragmentHomeBinding
    {
        return FragmentHomeBinding.inflate(inflater,container,false)
    } // onCreateView closed

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


       initViews()

    } // onViewCreated closed



    override fun initViews()
    {
        Log.d(TAG, "initViews: ")
        memoryViewModel.memory = null
        setupRecyclerView(binding.fragmentHomeRecyclerView)
        subscribeToMemories()
        memoryViewModel.getAllMemories()
        binding.fragmentHomeFloatingActionButton.setOnClickListener(this)
        binding.fragmentHomeReload.setOnClickListener(this)
    }

    override fun onResume()
    {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }
    
    private fun subscribeToMemories()
    {
        memoryViewModel.allMemoriesResponse.observe(viewLifecycleOwner)
        {
            when(it)
            {
                is NetworkResponse.Loading->
                {
                    binding.fragmentHomeProgressBar.show()
                    Log.d(TAG, "subscribeToMemories: loading")
                }
                is NetworkResponse.Error ->
                {
                    binding.fragmentHomeProgressBar.hide()
                    Log.d(TAG, "subscribeToMemories: error"+it.msg)
                }
                is NetworkResponse.Success ->
                {
                    binding.fragmentHomeProgressBar.hide()
                    Log.d(TAG, "subscribeToMemories: success"+it.data)
                    adapter?.submitList(it.data)
                }
            } // when closed
        } // observer closed

    } // subscribeToMemories
    private fun setupRecyclerView(recyclerView: RecyclerView)
    {
        adapter = MemoryAdapter()
        {
            memoryViewModel.memory = it
            findNavController().navigate(R.id.action_homeFragment_to_viewMemoryFragment)
        }


        recyclerView.layoutManager = GridLayoutManager(requireContext(),3)
        recyclerView.adapter = adapter


        Log.d(TAG, "setupRecyclerView: "+adapter?.currentList)


    }





    override fun onClick(view: View?)
    {
        when(view?.id)
        {
            R.id.fragmentHomeFloatingActionButton ->
            {
                findNavController().navigate(R.id.action_homeFragment_to_addMemoryFragment)
            }
            R.id.fragmentHomeReload ->
            {
                memoryViewModel.getAllMemories()
            }
        } // when closed
    } // onClick closed

}