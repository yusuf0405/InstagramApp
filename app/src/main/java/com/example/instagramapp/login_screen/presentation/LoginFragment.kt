package com.example.instagramapp.login_screen.presentation

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.instagramapp.R
import com.example.instagramapp.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val binding: LoginFragmentBinding by lazy(LazyThreadSafetyMode.NONE) {
        LoginFragmentBinding.inflate(layoutInflater)
    }
    private val viewModel: LoginViewModel by viewModels()

    private val progressDialog: ProgressDialog by lazy(LazyThreadSafetyMode.NONE) {
        ProgressDialog(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressDialog.setTitle("Progress Bar")
        progressDialog.setMessage("Application is loading, please wait")
        progressDialog.setCancelable(false)

        binding.goToCreateLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signFragment)
        }
        binding.enterBtn.setOnClickListener {
            binding.apply {
                when {
                    TextUtils.isEmpty(editLoginLog.text) -> {
                        editLoginLog.error = "Email name is required!"
                    }
                    TextUtils.isEmpty(editPasswordLog.text) -> {
                        editPasswordLog.error = "Password name is required!"
                    }
                    else -> {
                        viewModel.login(
                            email = editLoginLog.text.toString(),
                            password = editPasswordLog.text.toString())
                        viewVisibility(true)
                    }
                }
            }
        }

        viewModel.exception.observe(viewLifecycleOwner) {
            viewVisibility(false)
            showToast(it.message.toString())
        }
        viewModel.state.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                viewVisibility(false)
                onBoardingFinished()
                findNavController().navigate(R.id.action_loginFragment_to_homeActivity)
                showToast("Welcome!!")
            } else {
                viewVisibility(false)
                showToast("Invalid username/password.")
            }
        }

    }

    private fun viewVisibility(status: Boolean) {
        if (!status) progressDialog.show()
        else progressDialog.dismiss()
    }

    private fun showToast(msg: String) =
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()


    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }
}