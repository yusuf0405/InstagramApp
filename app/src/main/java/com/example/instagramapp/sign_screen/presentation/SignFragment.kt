package com.example.instagramapp.sign_screen.presentation

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
import com.example.instagramapp.login_screen.domain.models.User
import com.example.instagramapp.databinding.SignFragmentBinding
import com.parse.ParseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignFragment : Fragment() {

    private val binding: SignFragmentBinding by lazy(LazyThreadSafetyMode.NONE) {
        SignFragmentBinding.inflate(layoutInflater)
    }
    private val viewModel: SignViewModel by viewModels()

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

        binding.goToLoginFragment.setOnClickListener {
            findNavController().navigate(R.id.action_signFragment_to_loginFragment)
        }
        binding.registerButton.setOnClickListener {
            binding.apply {
                when {
                    TextUtils.isEmpty(editNameCreate.text) -> editNameCreate.error =
                        "Name is required!"

                    TextUtils.isEmpty(editLoginCreate.text) -> editLoginCreate.error =
                        "Email is required!"

                    TextUtils.isEmpty(editPasswordCreate.text) -> editPasswordCreate.error =
                        "Password is required!"

                    else -> {
                        viewVisibility(true)
                        val user = User(
                            email = editLoginCreate.text.toString(),
                            password = editPasswordCreate.text.toString(),
                            username = editNameCreate.text.toString())
                        viewModel.sign(user = user)
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
                ParseUser.logIn(
                    binding.editLoginCreate.text.toString(),
                    binding.editPasswordCreate.text.toString())
                findNavController().navigate(R.id.action_signFragment_to_homeActivity)
                showToast("Welcome!")
            } else {
                viewVisibility(false)
                showToast(response.message().toString())
            }
        }

    }

    private fun viewVisibility(status: Boolean) {
        if (status) progressDialog.show()
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