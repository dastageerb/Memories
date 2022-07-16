package com.example.memories.ui.auth

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.memories.R
import com.example.memories.base.BaseFragment
import com.example.memories.databinding.FragmentVerifyOtpBinding
import com.example.memories.ui.auth.viewmodel.AuthViewModel
import com.qrcodescanner.barcodescanner.qrgenerator.barcodegenerator.utils.extensionFunctions.ContextExtension.showToast


class VerifyOtpFragment : BaseFragment<FragmentVerifyOtpBinding>(),View.OnClickListener
{
    private  val viewModel: AuthViewModel by viewModels()

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, b: Boolean): FragmentVerifyOtpBinding
    {
        return FragmentVerifyOtpBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        binding.fragmentVerifyOtpButton.setOnClickListener(this)

    } // onViewCreated closed

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
            viewModel.verifyOtp(pinCode,"")
        else
            requireContext().showToast(validated.second)
    }


}