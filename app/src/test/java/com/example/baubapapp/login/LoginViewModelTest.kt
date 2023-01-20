package com.example.baubapapp.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.baubapapp.MainCoroutineRule
import com.example.baubapapp.domain.usecases.LoginUseCase
import com.example.baubapapp.presentation.login.LoginFormState
import com.example.baubapapp.presentation.login.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import com.example.baubapapp.common.Result
import com.example.baubapapp.domain.model.User
import com.example.baubapapp.presentation.login.LoginState

@ExperimentalCoroutinesApi
class LoginViewModelTest {

  @ExperimentalCoroutinesApi
  @get:Rule
  val mainCoroutineRule = MainCoroutineRule()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  var loginUseCase: LoginUseCase = mock()

  private lateinit var loginViewModel: LoginViewModel

  @Before
  fun setUp() {
    loginViewModel = LoginViewModel(loginUseCase)
  }

  @Test
  fun login_invalidEmail() {
    val email = "roberto93lazaro"
    val password = "Password12345"

    loginViewModel.attemptLogin(email, password)

    assertTrue(loginViewModel.loginFormState.value is LoginFormState.EmailError)
    assertTrue(loginViewModel.loginFormState.value !is LoginFormState.PasswordError)
  }

  @Test
  fun login_invalidPassword() {
    val email = "roberto93lazaro@gmail.com"
    val password = "pss"

    loginViewModel.attemptLogin(email, password)

    assertTrue(loginViewModel.loginFormState.value is LoginFormState.PasswordError)
    assertTrue(loginViewModel.loginFormState.value !is LoginFormState.EmailError)
  }

  @Test
  fun login_requestSuccessful() = runTest {
    val email = "roberto93lazaro@gmail.com"
    val password = "Password12345"

    whenever(loginUseCase.invoke(any(), any()))
      .thenReturn(Result.Success(User(name = "Roberto Lazaro", email = "roberto93lazaro@gmail.com")))

    loginViewModel.attemptLogin(email, password)

    verify(loginUseCase, times(1)).invoke(any(), any())

    assertThat(loginViewModel.loginState.value is LoginState.Success, `is`(true))
  }

  @Test
  fun login_requestError() = runTest {
    val email = "roberto@gmail.com"
    val password = "PasswordIncorrect"

    whenever(loginUseCase.invoke(any(), any()))
      .thenReturn(Result.Error("Unknown error message."))

    loginViewModel.attemptLogin(email, password)

    verify(loginUseCase, times(1)).invoke(any(), any())

    assertThat(loginViewModel.loginState.value is LoginState.Error, `is`(true))
  }
}