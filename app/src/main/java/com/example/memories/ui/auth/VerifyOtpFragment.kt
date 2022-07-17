package com.example.memories.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.memories.R
import com.example.memories.base.BaseFragment
import com.example.memories.databinding.FragmentVerifyOtpBinding
import com.example.memories.utils.extensionFunctions.ExtensionFunctions.hide
import com.example.memories.utils.extensionFunctions.ExtensionFunctions.show
import com.example.memories.utils.stateManagement.NetworkResponse
import com.qrcodescanner.barcodescanner.qrgenerator.barcodegenerator.utils.extensionFunctions.ContextExtension.showToast
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class VerifyOtpFragment : BaseFragment<FragmentVerifyOtpBinding>(),View.OnClickListener
{
    private  val viewModel: AuthViewModel by sharedViewModel()

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, b: Boolean): FragmentVerifyOtpBinding
    {
        return FragmentVerifyOtpBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        initViews()

    } // onViewCreated closed



    override fun initViews()
    {
        binding.fragmentVerifyOtpButton.setOnClickListener(this)
        subscribeToVerifyOtpResponse()

    } // initViews closed

    private fun subscribeToVerifyOtpResponse()
    {
        viewModel.response.asLiveData().observe(viewLifecycleOwner)
        {
            when(it)
            {
                is NetworkResponse.Loading -> binding.fragmentVerifyOtpProgressBar.show()

                is NetworkResponse.Error ->
                {
                    binding.fragmentVerifyOtpProgressBar.hide()
                    requireContext().showToast(it.msg.toString())
                }
                is NetworkResponse.Success ->
                {
                    binding.fragmentVerifyOtpProgressBar.hide()
                    findNavController().navigate(R.id.action_verifyOtpFragment_to_profileFragment)
                } // success closed
            }// when closed

        } // observer closed
    } // subscribeToVerifyOtpResponse


    override fun onClick(view: View?)
    {
        when(view?.id)
        {
            R.id.fragmentVerifyOtpButton ->
            {
                verifyOtp(binding.fragmentVerifyOtpNumberEditText.text.toString())
            }
        } //
    } // onClick closed

    private fun verifyOtp(pinCode: String)
    {
        val validated = viewModel.validatePinCode(pinCode)
        if (validated.first)
            viewModel.verifyOtp(pinCode)
        else
            requireContext().showToast(validated.second)
    }


}