package com.mofosoft.chatz.data.registerData

data class RegisterUiState(
    var name : String = "",
    var phone : String = "",
    var email : String = "",
    var new_password : String = "",
    var confirm_password: String = "",

    var nameError : Boolean = false,
    var phoneError : Boolean = false,
    var emailError : Boolean = false,
    var newPasswordError : Boolean = false,
    var confirmPasswordError : Boolean = false,
)
