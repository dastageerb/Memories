package com.example.memories.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.memories.R
import com.example.memories.base.BaseFragment
import com.example.memories.databinding.FragmentRegisterBinding
import com.example.memories.ui.auth.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterFragment : BaseFragment<FragmentRegisterBinding>(),View.OnClickListener
{


    private  val authViewModel: AuthViewModel by viewModel()

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, b: Boolean): FragmentRegisterBinding
    {
        return FragmentRegisterBinding.inflate(inflater,container,false)
    } // createView closed


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        initView()

    } // onViewCreated

    private fun initView()
    {

        binding.fragmentRegisterButtonRegister.setOnClickListener(this)
        binding.fragmentRegisterButtonGoToLogin.setOnClickListener(this)

    } // initView closed

    override fun onClick(view: View?)
    {
        when(view?.id)
        {
            R.id.fragmentRegisterButtonGoToLogin->
            {
                findNavController().popBackStack()
            }
            R.id.fragmentRegisterButtonRegister ->
            {
                register()
            }
        }
    }

    private fun register()
    {
        val name = binding.fragmentRegisterEditTextName.text.toString().trim()
        val phone = binding.fragmentRegisterEditTextNumber.text.toString().trim()

        val validate = authViewModel.validateRegisterEntities(name,phone)

    }

}