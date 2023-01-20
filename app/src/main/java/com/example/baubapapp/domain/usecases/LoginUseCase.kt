package com.example.baubapapp.domain.usecases


import com.example.baubapapp.domain.model.User
import com.example.baubapapp.domain.repository.LoginRepository
import javax.inject.Inject
import com.example.baubapapp.common.Result


class LoginUseCase @Inject constructor(
  private val loginRepository: LoginRepository
) {

  suspend operator fun invoke(email: String, password: String): Result<User> {
    return loginRepository.login(email, password)
  }
}