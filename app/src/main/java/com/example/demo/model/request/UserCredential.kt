package com.example.demo.model.request

data class UserCredential (val Email: String, val Password: String, val Idfa: String = "ANYTHING")