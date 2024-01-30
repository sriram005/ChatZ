package com.mofosoft.chatz.data.rules

import android.util.Patterns

object Validator {
    fun validateName(name : String)  : ValidationResult {
        return ValidationResult(
            (name.isNotEmpty() && name.length >= 3)
        )
    }
    fun validatePhone(phone : String) : ValidationResult {
        return ValidationResult(
            (phone.isNotEmpty() && Patterns.PHONE.matcher(phone).matches() && phone.length==10)
        )
    }
    fun validateEmail(email : String) : ValidationResult {
        return ValidationResult(
            (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())
        )
    }
    fun validatePassword(password : String) : ValidationResult{
        return ValidationResult(
            (password.isNotEmpty() && password.length>=6)
        )
    }
    fun validateConfirmPassword(password : String, confirmPassword : String) : ValidationResult{
        return ValidationResult(
            (confirmPassword.isNotEmpty() && confirmPassword== password )
        )
    }
}

data class ValidationResult(
    val status : Boolean = false
)