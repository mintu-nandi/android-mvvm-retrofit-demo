package com.example.demo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.model.response.UserSession
import com.example.demo.api.status.Result
import com.example.demo.model.request.UserCredential
import com.example.demo.repository.AuthenticationRepository
import com.example.demo.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val authenticationRepository: AuthenticationRepository) :
    ViewModel() {
    private val _user: MutableLiveData<Result<UserSession>> = MutableLiveData()
    private val _isFieldsValid: MutableLiveData<Boolean> = MutableLiveData()

    fun getUserSession(): LiveData<Result<UserSession>> = _user
    fun getUserSessionData(user: UserCredential) = viewModelScope.launch {
        _user.value = Result.Loading
        _user.value = authenticationRepository.getUserData(user)
    }

    val isFieldsValid: LiveData<Boolean>
        get() = _isFieldsValid

    var email = ""
        set(value) {
            field = value
            validateForm()
        }

    var password = ""
        set(value) {
            field = value
            validateForm()
        }

    var name = ""
        set(value) {
            field = value
            validateForm()
        }

    private fun validateForm() {
        val isEmailValid = Pattern.matches(Constants.EMAIL_REGEX_CHARACTERS, email)
        val isPasswordValid = Pattern.matches(Constants.PASSWORD_REGEX_CHARACTERS, password)
        val isNameValid: Boolean

        if(isEmailValid && isPasswordValid) {
            _isFieldsValid.postValue(isEmailValid && isPasswordValid)
        }

        if(name.isNotBlank()) {
            isNameValid = Pattern.matches(Constants.NAME_REGEX_CHARACTERS, name)
            _isFieldsValid.postValue(isNameValid)
        }
    }
}