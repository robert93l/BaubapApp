package com.example.baubapapp.presentation.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.baubapapp.R
import com.example.baubapapp.databinding.LoginFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

  private val viewModel by viewModels<LoginViewModel>()

  private lateinit var binding: LoginFragmentBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = LoginFragmentBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setUpObservers()
    setUpListeners()
  }

  @SuppressLint("StringFormatInvalid")
  private fun setUpObservers() {
    with(viewModel) {
      loginFormState.observe(viewLifecycleOwner) { state ->

        clearErrors()

        when (state) {
          is LoginFormState.EmailError -> {
            binding.emailView.error = getString(state.error)
            binding.emailView.requestFocus()
          }
          is LoginFormState.PasswordError -> {
            binding.passwordView.error = getString(state.error)
            binding.passwordView.requestFocus()
          }
        }
      }

      loginState.observe(viewLifecycleOwner) { state ->
        when (state) {
          LoginState.Loading -> {
            showProgress()
          }
          is LoginState.Success -> {
            hideProgress()
            showLoginDialog(getString(R.string.label_login_success, state.user.name))
          }
          is LoginState.Error -> {
            hideProgress()
            showLoginDialog(getString(state.error))
          }
        }
      }
    }
  }

  private fun setUpListeners() {
    with(binding) {
      loginButton.setOnClickListener {
        loginWithCredentials()
      }

      passwordView.setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
          loginWithCredentials()
        }
        false
      }
    }
  }

  private fun loginWithCredentials() {
    val email = binding.emailView.text.toString()
    val password = binding.passwordView.text.toString()
    viewModel.attemptLogin(email, password)
  }

  private fun showProgress() {
    binding.loginProgressBar.visibility = VISIBLE
    binding.loginButton.visibility = GONE
  }

  private fun hideProgress() {
    binding.loginProgressBar.visibility = GONE
    binding.loginButton.visibility = VISIBLE
  }

  private fun clearErrors() {
    binding.emailView.error = null
    binding.passwordView.error = null
  }

  private fun showLoginDialog(message: String) {
    MaterialAlertDialogBuilder(requireContext())
      .setMessage(message)
      .setPositiveButton(android.R.string.ok) { dialog, _ ->
        dialog.dismiss()
      }
      .show()
  }
}