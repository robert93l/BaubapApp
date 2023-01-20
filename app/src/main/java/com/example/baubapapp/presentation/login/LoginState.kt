package com.example.baubapapp.presentation.login

import com.example.baubapapp.domain.model.User

sealed class LoginState {

  object Loading : LoginState()

  data class Success(val user: User) : LoginState()

  data class Error(val error: Int) : LoginState()
}
