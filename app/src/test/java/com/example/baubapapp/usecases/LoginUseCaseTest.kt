package com.example.baubapapp.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.baubapapp.MainCoroutineRule
import com.example.baubapapp.common.Result
import com.example.baubapapp.domain.model.User
import com.example.baubapapp.domain.repository.LoginRepository
import com.example.baubapapp.domain.usecases.LoginUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoginUseCaseTest {

  @ExperimentalCoroutinesApi
  @get:Rule
  val mainCoroutineRule = MainCoroutineRule()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  var loginRepository: LoginRepository = mock()

  private lateinit var loginUseCase: LoginUseCase

  @Before
  fun setUp() {
    loginUseCase = LoginUseCase(loginRepository)
  }

  @Test
  fun login_success() = runTest {
    val email = "roberto93lazaro@gmail.com"
    val password = "Password12345"

    whenever(loginRepository.login(any(), any()))
      .thenReturn(Result.Success(User(name = "Roberto Lazaro", email = "roberto93lazaro@gmail.com")))

    val response = loginUseCase(email, password)

    assertThat(response is Result.Success, `is`(true))
    assertThat((response as Result.Success).data.name, `is`("Roberto Lazaro"))
    assertThat(response.data.email, `is`("roberto93lazaro@gmail.com"))
  }

  @Test
  fun login_error() = runTest {
    val email = "roberto93lazaro@gmail.com"
    val password = "Password12345"

    whenever(loginRepository.login(any(), any()))
      .thenReturn(Result.Error("Unknown error message."))

    val response = loginUseCase(email, password)

    assertThat(response is Result.Error, `is`(true))
    assertThat((response as Result.Error).message, `is`("Unknown error message."))
  }
}