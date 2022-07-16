package com.example.memories.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.memories.R
import com.example.memories.base.BaseFragment
import com.example.memories.databinding.FragmentLoginBinding
import com.example.memories.ui.auth.viewmodel.AuthViewModel
import com.example.memories.utils.extensionFunctions.ExtensionFunctions.show
import com.example.memories.utils.stateManagement.NetworkResponse
import com.qrcodescanner.barcodescanner.qrgenerator.barcodegenerator.utils.extensionFunctions.ContextExtension.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel


class LogInFragment : BaseFragment<FragmentLoginBinding>(), View.OnClickListener

{


    private  val authViewModel: AuthViewModel by viewModel()


    override fun createView(inflater: LayoutInflater, container: ViewGroup?, b: Boolean): FragmentLoginBinding
    {
        return FragmentLoginBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        initViews()

    } // onViewCreated closed


    private fun initViews()
    {

        if (authViewModel.currentUser()!=null)
            findNavController().setGraph(R.navigation.base_nav)

        binding.fragmentLoginButtonLogin.setOnClickListener(this)
        binding.fragmentLoginButtonGoToRegister.setOnClickListener(this)
        subscribeToOtpCallbackObserver()

    } // initViews closed



    private fun subscribeToOtpCallbackObserver()
    {
        authViewModel.response.asLiveData().observe(viewLifecycleOwner)
        {
            when(it)
            {
                is NetworkResponse.Loading -> binding.fragmentLoginProgressBar.show()
                is NetworkResponse.Error -> requireContext().showToast(it.msg.toString())
                is NetworkResponse.Success ->
                {
                    authViewModel.verificationId = it.data?.second
                    findNavController().navigate(R.id.action_logInFragment_to_verifyOtpFragment)
                }
            } // when closed


        } // response closed
    } //


    override fun onClick(view: View)
    {
        when(view.id)
        {
            R.id.fragmentLoginButtonLogin -> login(binding.fragmentLoginButtonLogin.text.toString())
            R.id.fragmentLoginButtonGoToRegister -> findNavController()
                .navigate(R.id.action_logInFragment_to_registerFragment)

        } // when closed
    }

    private fun login(number: String)
    {
        val validated = authViewModel.isNumberValid(number)

        if (validated.first)
            authViewModel.sendOtp(number,requireActivity())
        else
            requireContext().showToast(validated.second)
    } // login closed



}