package com.example.baubapapp.presentation.login

sealed class LoginFormState {

  data class EmailError(val error: Int) : LoginFormState()

  data class PasswordError(val error: Int) : LoginFormState()
}
