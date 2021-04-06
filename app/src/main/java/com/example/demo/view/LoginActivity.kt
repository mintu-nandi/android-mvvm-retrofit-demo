package com.example.demo.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.demo.api.status.Result
import com.example.demo.R
import com.example.demo.model.request.UserCredential
import com.example.demo.model.response.UserSession
import com.example.demo.util.SharedPreferencesUtil
import com.example.demo.util.SharedPreferencesUtil.token
import com.example.demo.util.SharedPreferencesUtil.username
import com.example.demo.util.toast
import com.example.demo.viewmodel.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*

/**
 * A login screen that offers login via email/password.
 */

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupViews()
        initDataObserver()
    }

    private fun setupViews() {
        et_email.doAfterTextChanged { text -> viewModel.email = text?.toString() ?: "" }
        et_password.doAfterTextChanged { text -> viewModel.password = text?.toString() ?: "" }
        et_name.doAfterTextChanged { text -> viewModel.name = text?.toString() ?: "" }

        viewModel.isFieldsValid.observe(this, Observer {
            btn_sign_in.isEnabled = it ?: false
        })

        btn_sign_in.setOnClickListener {
            animation.playAnimation()
            initViewModel(
                et_email.text.toString(),
                et_password.text.toString(),
                et_name.text.toString()
            )
        }
    }

    private fun initViewModel(username: String, password: String, name: String) {
        val nameField = if(name.isBlank()) "ANYTHING" else name
        val userData = UserCredential(username, password, nameField)
        viewModel.getUserSessionData(userData)
    }

    private fun initDataObserver() {
        viewModel.getUserSession().observe(this, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    animation.repeatCount = 20
                }
                is Result.Success -> {
                    animation.repeatCount = 0
                    updateData(result.data)
                }
                is Result.Error -> {
                    animation.repeatCount = 0
                    toast(result.message)
                }
            }
        })
    }

    private fun updateData(data: UserSession) {
        SharedPreferencesUtil.sharedPreference(this).username = "Hello ${et_name.text}!"
        SharedPreferencesUtil.sharedPreference(this).token = data.session.token
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        finish()
    }
}
