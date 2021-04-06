package com.example.demo.util

object Constants {

    const val APP_ID = "3a97b932a9d449c981b595"
    const val CONTENT_TYPE = "application/json"
    const val APP_VERSION = "7.15.0"
    const val API_VERSION = "3.0.0"

    const val CONNECTION_TIMEOUT : Long = 20 // 20 seconds

    const val BASE_URL = "https://api-test01.moneyboxapp.com/"

    const val EMAIL_REGEX_CHARACTERS = "[^@]+@[^.]+\\..+"
    const val PASSWORD_REGEX_CHARACTERS = "^(?=.*[0-9])(?=.*[A-Z]).{10,50}$"
    const val NAME_REGEX_CHARACTERS = "[a-zA-Z\\s]{6,30}"
}