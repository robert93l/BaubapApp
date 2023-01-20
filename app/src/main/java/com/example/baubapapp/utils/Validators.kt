package com.example.baubapapp.utils

fun String.isInvalidEmail(): Boolean {
  return !isEmailValid()
}

fun String.isEmailValid(): Boolean {
  return "(\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,6})".toRegex().matches(this)
}

fun String.isInvalidPassword(): Boolean {
  return !isPasswordValid()
}

fun String.isPasswordValid(): Boolean {
  return this.length > 8
}