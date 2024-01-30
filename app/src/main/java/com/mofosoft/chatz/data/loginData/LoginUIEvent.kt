package com.mofosoft.chatz.data.loginData

sealed class LoginUIEvent{
    data class EmailChanged(val email : String) : LoginUIEvent()
    data class PasswordChanged(val new_password : String) : LoginUIEvent()
}