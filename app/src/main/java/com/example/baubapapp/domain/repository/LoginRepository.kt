package com.example.baubapapp.domain.repository

import com.example.baubapapp.domain.model.User
import com.example.baubapapp.common.Result

interface LoginRepository {

  suspend fun login(email: String, password: String): Result<User>
}