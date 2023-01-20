package com.example.baubapapp.data.remote

import com.example.baubapapp.data.remote.dto.LoginResponse
import com.example.baubapapp.common.Result

object LoginApi {

  private val users = mutableMapOf(
    "roberto93lazaro@gmail.com" to "Password12345"
  )

  fun login(email: String, password: String): Result<LoginResponse> {
    users[email]?.let {
      if (it == password) {
        return Result.Success(
          LoginResponse(
            id = 1,
            firstName = "Roberto",
            lastName = "Lazaro",
            email = "roberto93lazaro@gmail.com"
          )
        )
      }
    }
    return Result.Error("Invalid email or password")
  }
}