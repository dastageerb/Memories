package com.example.memories.ui.memory

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.memories.R
import com.example.memories.base.BaseFragment
import com.example.memories.databinding.FragmentAddMemoryBinding
import com.example.memories.model.Memory
import com.example.memories.utils.BitmapHelper
import com.example.memories.utils.Constants.TAG
import com.example.memories.utils.extensionFunctions.ExtensionFunctions.hide
import com.example.memories.utils.extensionFunctions.ExtensionFunctions.show
import com.example.memories.utils.stateManagement.NetworkResponse
import com.qrcodescanner.barcodescanner.qrgenerator.barcodegenerator.utils.extensionFunctions.ContextExtension.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import javax.annotation.meta.When
import kotlin.math.log


class AddMemoryFragment : BaseFragment<FragmentAddMemoryBinding>(), View.OnClickListener
{

    private val memoryViewModel:MemoryViewModel by viewModel()


    private var bitmap: Bitmap?=null

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, b: Boolean): FragmentAddMemoryBinding
    {
        return FragmentAddMemoryBinding.inflate(inflater,container,false)
    } // createView closed

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        initViews()


    } // onViewCreated closed

    private fun initViews()
    {

        binding.fragmentMemoryButtonAddMemory.setOnClickListener(this)
        binding.fragmentMemoryImageView.setOnClickListener(this)

        subscribeAddMemoryResponse()
    }

    private fun subscribeAddMemoryResponse()
    {
            memoryViewModel.memoryResponse.asLiveData().observe(viewLifecycleOwner)
            {
                when(it)
                {
                    is NetworkResponse.Loading ->
                    {
                        Log.d(TAG, "subscribeAddMemoryResponse:loading ")
                        binding.fragmentAddMemoryProgressBar.show()
                    }
                    is NetworkResponse.Error ->
                    {
                        Log.d(TAG, "subscribeAddMemoryResponse:error ")
                        binding.fragmentAddMemoryProgressBar.hide()
                        requireContext().showToast(it.msg.toString())
                    }
                    is NetworkResponse.Success ->
                    {
                        Log.d(TAG, "subscribeAddMemoryResponse: sucess")
                        binding.fragmentAddMemoryProgressBar.hide()
                        it.data?.message?.let()
                        { it1 ->
                            requireContext().showToast(it1)
                            findNavController().navigate(R.id.action_addMemoryFragment_to_homeFragment)
                        } // it closed
                    } // success closed
                } // when closed
            } // observer closed
    } // subscribeAddMemoryResponse


    private fun getImageFromGallery()
    {
        selectImageFromGalleryResult.launch("image/*")
    } // getImageFromGallery closed



    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent())
    { uri: Uri? ->
        uri?.let()
        {
            binding.fragmentMemoryImageView.setImageURI(uri)
            bitmap = BitmapHelper.getBitmapFromUri(requireActivity(),uri)!!
        } //
    } //

    override fun onClick(view: View)
    {
        when(view.id)
        {
            R.id.fragmentMemoryButtonAddMemory ->
            {
                val description = binding.fragmentMemoryDescriptionTextView.text.toString().trim()
                addMemory(description)
            }

            R.id.fragmentMemoryImageView ->
            {
                getImageFromGallery()
            }

        } // when closed
    } // onclick closed


    private fun addMemory(description: String)
    {
        val validated = memoryViewModel.validateMemory(description,bitmap)
        if (!validated.first)
            requireContext().showToast(validated.second)
        else memoryViewModel.addMemory(description,bitmap!!)

    } // addMemory closed


} // AddMemoryFragment closed