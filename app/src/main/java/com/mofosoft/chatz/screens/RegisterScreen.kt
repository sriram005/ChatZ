package com.mofosoft.chatz.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mofosoft.chatz.ChatViewModel
import com.mofosoft.chatz.CommonProgressBar
import com.mofosoft.chatz.R
import com.mofosoft.chatz.CheckIfRegistered
import com.mofosoft.chatz.data.registerData.RegisterUIEvent
import com.mofosoft.chatz.data.registerData.RegisterViewModel
import com.mofosoft.chatz.myRouter.Screen

@Composable
fun RegisterScreen(
    navController: NavController,
    chatViewModel: ChatViewModel
) {

    CheckIfRegistered(navController = navController, chatViewModel = chatViewModel)

    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    var new_password by remember { mutableStateOf("") }
    var new_password_visible by remember { mutableStateOf(false) }

    var confirm_password by remember { mutableStateOf("") }
    var confirm_password_visible by remember { mutableStateOf(false) }

    val registerViewModel : RegisterViewModel = viewModel()
    chatViewModel.context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box (
            modifier = Modifier.fillMaxSize()
        ){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.5f),
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = "Create \n\nAccount",
                            fontSize = 36.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clip(shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                            .background(color = MaterialTheme.colorScheme.background),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(.72f),
                            value = name,
                            onValueChange = {
                                name = it
                                registerViewModel.onEvent(RegisterUIEvent.NameChanged(it))
                            },
                            placeholder = { Text(text = "Name") },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Text
                            ),
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Person,
                                    contentDescription = "Person-icon"
                                )
                            },
                            isError = !(registerViewModel.registerUiState.value.nameError)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(.72f),
                            value = phone,
                            onValueChange = {
                                phone = it
                                registerViewModel.onEvent(RegisterUIEvent.PhoneChanged(it))
                            },
                            placeholder = { Text(text = "Phone") },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Number
                            ),
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Call,
                                    contentDescription = "Email-icon"
                                )
                            },
                            isError = !(registerViewModel.registerUiState.value.phoneError)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(.72f),
                            value = email,
                            onValueChange = {
                                email = it
                                registerViewModel.onEvent(RegisterUIEvent.EmailChanged(it))
                            },
                            placeholder = { Text(text = "Email") },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Email
                            ),
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Email,
                                    contentDescription = "Email-icon"
                                )
                            },
                            isError = !(registerViewModel.registerUiState.value.emailError)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // new Password
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(.72f),
                            value = new_password,
                            onValueChange = {
                                new_password = it
                                registerViewModel.onEvent(RegisterUIEvent.PasswordChanged(it))
                            },
                            placeholder = { Text(text = "Password") },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Password
                            ),
                            shape = RoundedCornerShape(8.dp),
                            visualTransformation = if(new_password_visible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val image = if(new_password_visible) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24
                                Icon(
                                    imageVector = ImageVector.vectorResource(image),
                                    contentDescription = "Password-Icon",
                                    modifier = Modifier.clickable {
                                        new_password_visible = !new_password_visible
                                    }
                                )
                            },
                            isError = !(registerViewModel.registerUiState.value.newPasswordError)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        //Confirm Password
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(.72f),
                            value = confirm_password,
                            onValueChange = {
                                confirm_password = it
                                registerViewModel.onEvent(RegisterUIEvent.ConfirmPasswordChanged(it))
                            },
                            placeholder = { Text(text = "Confirm Password") },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Password
                            ),
                            shape = RoundedCornerShape(8.dp),
                            visualTransformation = if(confirm_password_visible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val image = if(confirm_password_visible) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24
                                Icon(
                                    imageVector = ImageVector.vectorResource(image),
                                    contentDescription = "Password-Icon",
                                    modifier = Modifier.clickable {
                                        confirm_password_visible = !confirm_password_visible
                                    }
                                )
                            },
                            isError = !(registerViewModel.registerUiState.value.confirmPasswordError)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {
                                chatViewModel.registerUser(
                                    name = registerViewModel.registerUiState.value.name,
                                    email = registerViewModel.registerUiState.value.email,
                                    number = registerViewModel.registerUiState.value.phone,
                                    password = registerViewModel.registerUiState.value.new_password,
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            enabled = registerViewModel.allValidationsPassed.value
                        ) {
                            Text(
                                text = "Register",
                                fontSize = 16.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                text = "Already have an Account?",
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            TextButton(
                                onClick = {
                                    navController.navigate(Screen.login.route)
                                },
                                modifier = Modifier.padding(0.dp)
                            ) {
                                Text(text = "Login")
                            }
                        }
                    }
                }
            }
        }
        if(chatViewModel.inProgress.value){
            CommonProgressBar()
        }
    }

}