package com.example.baubapapp.presentation.login



import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baubapapp.R
import com.example.baubapapp.domain.usecases.LoginUseCase
import com.example.baubapapp.presentation.login.LoginFormState.EmailError
import com.example.baubapapp.presentation.login.LoginFormState.PasswordError
import com.example.baubapapp.utils.isInvalidEmail
import com.example.baubapapp.utils.isInvalidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.baubapapp.common.Result

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val loginUseCase: LoginUseCase
) : ViewModel() {

  private val _loginFormState = MutableLiveData<LoginFormState>()
  val loginFormState: LiveData<LoginFormState> = _loginFormState

  private val _loginState = MutableLiveData<LoginState>()
  val loginState: LiveData<LoginState> = _loginState



  fun attemptLogin(email: String, password: String) {
    if (email.isBlank()) {
      _loginFormState.value = EmailError(error = R.string.error_empty_email)
      return
    }
    if (password.isBlank()) {
      _loginFormState.value = PasswordError(error = R.string.error_empty_password)
      return
    }

    when {
      email.isInvalidEmail() -> {
        _loginFormState.value = EmailError(error = R.string.error_invalid_email)
      }
      password.isInvalidPassword() -> {
        _loginFormState.value = PasswordError(error = R.string.error_invalid_password)
      }
      else -> {
        login(email, password)
      }
    }
  }

  fun login(email: String, password: String) {
    viewModelScope.launch {
      _loginState.postValue(LoginState.Loading)
      when (val response = loginUseCase(email, password)) {
        is Result.Success -> _loginState.postValue((LoginState.Success(user = response.data)))
        is Result.Error -> _loginState.postValue(LoginState.Error(error = R.string.error_login_failed))
      }
    }
  }
}