package com.example.memories.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.memories.R
import com.example.memories.base.BaseFragment
import com.example.memories.databinding.FragmentSendOtpBinding
import com.example.memories.utils.extensionFunctions.ExtensionFunctions.hide
import com.example.memories.utils.extensionFunctions.ExtensionFunctions.show
import com.example.memories.utils.stateManagement.NetworkResponse
import com.qrcodescanner.barcodescanner.qrgenerator.barcodegenerator.utils.extensionFunctions.ContextExtension.showToast
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SendOtpFragment : BaseFragment<FragmentSendOtpBinding>(), View.OnClickListener

{


    private  val authViewModel: AuthViewModel by sharedViewModel()


    override fun createView(inflater: LayoutInflater, container: ViewGroup?, b: Boolean): FragmentSendOtpBinding
    {
        return FragmentSendOtpBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        initViews()

    } // onViewCreated closed


     override fun initViews()
    {

        if (authViewModel.currentUser()!=null)
            findNavController().setGraph(R.navigation.base_nav)

        binding.fragmentSendOtpButtonSend.setOnClickListener(this)
        subscribeToOtpCallbackObserver()

    } // initViews closed



    private fun subscribeToOtpCallbackObserver()
    {
        authViewModel.response.asLiveData().observe(viewLifecycleOwner)
        {
            when(it)
            {
                is NetworkResponse.Loading -> binding.fragmentLoginProgressBar.show()
                is NetworkResponse.Error ->
                {
                    binding.fragmentLoginProgressBar.hide()
                    requireContext().showToast(it.msg.toString())
                }
                is NetworkResponse.Success ->
                {
                    binding.fragmentLoginProgressBar.hide()
                    authViewModel.verificationId = it.data?.second
                    findNavController().navigate(R.id.action_sendOtpFragment_to_verifyOtpFragment)
                }
            } // when closed


        } // response closed
    } //


    override fun onClick(view: View)
    {
        when(view.id)
        {
            R.id.fragmentSendOtpButtonSend -> login(binding.fragmentSendOtpEditTextNumber.text.toString())
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