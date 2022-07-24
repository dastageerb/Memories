package com.example.memories.ui.memory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.memories.R
import com.example.memories.base.BaseFragment
import com.example.memories.databinding.FragmentViewMemoryBinding
import com.example.memories.utils.extensionFunctions.ExtensionFunctions.hide
import com.example.memories.utils.extensionFunctions.ExtensionFunctions.show
import com.example.memories.utils.stateManagement.NetworkResponse
import com.qrcodescanner.barcodescanner.qrgenerator.barcodegenerator.utils.extensionFunctions.ContextExtension.showToast
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ViewMemoryFragment : BaseFragment<FragmentViewMemoryBinding>(),View.OnClickListener
{


    private val memoryViewModel:MemoryViewModel by sharedViewModel()

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, b: Boolean): FragmentViewMemoryBinding
    {
        return FragmentViewMemoryBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        initViews()

    }


    override fun initViews()
    {
        binding.fragmentViewDeleteMemory.setOnClickListener(this)

        if (memoryViewModel.memory!=null)
        {
            val memory = memoryViewModel.memory

            binding.fragmentViewMemoryTextViewDescription.text = memory?.description
            binding.fragmentViewMemoryTextViewTimeStamp.text = memory?.timestamp?.toDate().toString()
                .substringBefore("GMT")
                .substringBeforeLast(":")
            binding.fragmentViewMemoryImageView.load(memory?.imageUrl)
        }

        subscribeToMemoryResponse()

    } // initViews closed

    private fun subscribeToMemoryResponse()
    {
        memoryViewModel.memoryResponse.asLiveData().observe(viewLifecycleOwner)
        {
            when(it)
            {
                is NetworkResponse.Loading->
                {
                    binding.fragmentViewMemoryProgressBar.show()
                }

                is NetworkResponse.Error ->
                {
                    binding.fragmentViewMemoryProgressBar.hide()
                    requireContext().showToast(it.msg.toString())
                }

                is NetworkResponse.Success->
                {
                    binding.fragmentViewMemoryProgressBar.hide()
                    requireContext().showToast(it.data?.message.toString())
                    findNavController().navigate(R.id.action_viewMemoryFragment_to_homeFragment)
                }
            } // when closed
        } /// subscribeTo MemoryResponse closed
    } // subscribeToMemoryResponse closed

    override fun onClick(view: View?)
    {
        when(view?.id)
        {

            R.id.fragmentViewDeleteMemory ->
            {
                if (memoryViewModel.memory == null)
                    return
                memoryViewModel.deleteMemory(memoryViewModel.memory?.memoryId!!)
            }
        }
    }

}