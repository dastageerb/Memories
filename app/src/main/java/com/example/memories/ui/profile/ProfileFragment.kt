package com.example.memories.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.memories.R
import com.example.memories.base.BaseFragment
import com.example.memories.databinding.FragmentProfileBinding
import com.example.memories.ui.auth.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : BaseFragment<FragmentProfileBinding>(),View.OnClickListener
{


    private  val authViewModel: AuthViewModel by viewModel()

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



    } // initView closed

    override fun onClick(view: View?)
    {
        when(view?.id)
        {

        }
    }

//    private fun register()
//    {
//        val name = binding.fragmentRegisterEditTextName.text.toString().trim()
//        val phone = binding.fragmentRegisterEditTextNumber.text.toString().trim()
//
//        val validate = authViewModel.validateRegisterEntities(name,phone)
//
//    }

}