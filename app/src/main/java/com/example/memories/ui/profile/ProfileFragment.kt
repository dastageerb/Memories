package com.example.memories.ui.profile

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.memories.R
import com.example.memories.base.BaseFragment
import com.example.memories.databinding.FragmentProfileBinding
import com.example.memories.model.User
import com.example.memories.utils.BitmapHelper
import com.example.memories.utils.extensionFunctions.ExtensionFunctions.hide
import com.example.memories.utils.extensionFunctions.ExtensionFunctions.show
import com.example.memories.utils.stateManagement.NetworkResponse
import com.qrcodescanner.barcodescanner.qrgenerator.barcodegenerator.utils.extensionFunctions.ContextExtension.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : BaseFragment<FragmentProfileBinding>(),View.OnClickListener
{


    private  val profileViewModel: ProfileViewModel by viewModel()
    private var user:User?=null
    private var bitmap:Bitmap?=null

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, b: Boolean): FragmentProfileBinding
    {
        return FragmentProfileBinding.inflate(inflater,container,false)
    } // createView closed


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    } // onViewCreated

    override fun initViews()
    {
        binding.fragmentProfileButtonNext.setOnClickListener(this)
        binding.fragmentProfileImageViewEditImage.setOnClickListener(this)

        profileViewModel.getProfile()

        subscribeToUserResponse()
        subscribeToUploadUserResponse()

    } // initView closed

    private fun subscribeToUserResponse()
    {
        profileViewModel.getUser.observe(viewLifecycleOwner)
        {
            when(it)
            {
                is NetworkResponse.Success ->
                {
                    if (it.data!=null)
                    {
                        user = it.data
                        binding.fragmentProfileImageViewProfile.load(user?.profileImage)
                        binding.fragmentProfileEditTextName.setText(user?.name)
                    }
                } // success closed
            } // when closed
        } // getUser closed
    } // subscribeToUserResponse

    private fun subscribeToUploadUserResponse()
    {
        profileViewModel.uploadUserResponse.asLiveData().observe(viewLifecycleOwner)
        {
            when(it)
            {
                is NetworkResponse.Loading -> binding.fragmentProfileProgressBar.show()
                is NetworkResponse.Error ->
                {
                    binding.fragmentProfileProgressBar.hide()
                    requireContext().showToast(it.msg.toString())
                }  // error closed
                is NetworkResponse.Success ->
                {
                    findNavController().setGraph(R.navigation.base_nav)
                } // success closed
            } // when closed
        } // uploadUserResponse
    } // subscribeToUploadUserResponse




    private fun getImageFromGallery()
    {
        selectImageFromGalleryResult.launch("image/*")
    } // getImageFromGallery closed



    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent())
    { uri: Uri? ->
        uri?.let()
        {
            binding.fragmentProfileImageViewProfile.setImageURI(uri)
            bitmap = BitmapHelper.getBitmapFromUri(requireActivity(),uri)!!
        } // uri closed
    } // select Image from gallery close








    override fun onClick(view: View?)
    {
        when(view?.id)
        {
            R.id.fragmentProfileButtonNext ->
            {
                val name  = binding.fragmentProfileEditTextName.text.toString()
                profileViewModel.validateAndUploadUser(name,bitmap,user)
            }
            R.id.fragmentProfileImageViewEditImage ->
            {
                getImageFromGallery()
            }
        }
    }





}