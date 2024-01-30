package com.mofosoft.chatz.data.registerData

sealed class RegisterUIEvent{
    data class NameChanged(val name : String) : RegisterUIEvent()
    data class PhoneChanged(val phone : String) : RegisterUIEvent()
    data class EmailChanged(val email : String) : RegisterUIEvent()
    data class PasswordChanged(val new_password : String) : RegisterUIEvent()
    data class ConfirmPasswordChanged(val confirm_password : String) : RegisterUIEvent()

}