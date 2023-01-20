package com.example.baubapapp.data.repository

import com.example.baubapapp.data.remote.LoginApi
import com.example.baubapapp.data.remote.dto.toUser
import com.example.baubapapp.domain.model.User
import com.example.baubapapp.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.example.baubapapp.common.Result

class LoginRepositoryImpl @Inject constructor() : LoginRepository {


  override suspend fun login(email: String, password: String): Result<User> =
    withContext(Dispatchers.IO) {
      delay(1000)
      return@withContext try {
        val response = LoginApi.login(email, password)
        Result.Success((response as Result.Success).data.toUser())
      } catch (e: Exception) {
        Result.Error(e.localizedMessage ?: "Unknown error message.")
      }
    }
}