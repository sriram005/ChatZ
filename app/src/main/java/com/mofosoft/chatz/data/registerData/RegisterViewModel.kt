package com.mofosoft.chatz.data.registerData

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mofosoft.chatz.data.rules.Validator

class RegisterViewModel : ViewModel() {

    var registerUiState = mutableStateOf(RegisterUiState())

    var allValidationsPassed = mutableStateOf(false)
    fun onEvent(event : RegisterUIEvent){
        when(event){
            is RegisterUIEvent.NameChanged -> {
                registerUiState.value = registerUiState.value.copy(
                    name = event.name
                )
                validateData()
            }

            is RegisterUIEvent.PhoneChanged -> {
                registerUiState.value = registerUiState.value.copy(
                    phone = event.phone
                )
                validateData()
            }

            is RegisterUIEvent.EmailChanged -> {
                registerUiState.value = registerUiState.value.copy(
                    email = event.email
                )
                validateData()
            }

            is RegisterUIEvent.PasswordChanged -> {
                registerUiState.value = registerUiState.value.copy(
                    new_password = event.new_password
                )
                validateData()
            }

            is RegisterUIEvent.ConfirmPasswordChanged -> {
                registerUiState.value = registerUiState.value.copy(
                    confirm_password = event.confirm_password
                )
                validateData()
            }

        }
    }


    private fun validateData() {
        val nameResult = Validator.validateName(
            name = registerUiState.value.name
        )

        val phoneResult = Validator.validatePhone(
            phone = registerUiState.value.phone
        )

        val emailResult = Validator.validateEmail(
            email = registerUiState.value.email
        )

        val passwordResult = Validator.validatePassword(
            password = registerUiState.value.new_password
        )

        val ConfirmPasswordResult = Validator.validateConfirmPassword(
            password = registerUiState.value.new_password,
            confirmPassword = registerUiState.value.confirm_password
        )

        registerUiState.value = registerUiState.value.copy(
            nameError = nameResult.status,
            phoneError = phoneResult.status,
            emailError = emailResult.status,
            newPasswordError = passwordResult.status,
            confirmPasswordError = ConfirmPasswordResult.status
        )

        allValidationsPassed.value = nameResult.status && phoneResult.status && emailResult.status && passwordResult.status && ConfirmPasswordResult.status
    }
}