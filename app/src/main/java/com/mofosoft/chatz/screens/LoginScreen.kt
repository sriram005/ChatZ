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
import androidx.compose.material.icons.filled.Email
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mofosoft.chatz.ChatViewModel
import com.mofosoft.chatz.CheckIfLogedIn
import com.mofosoft.chatz.R
import com.mofosoft.chatz.data.loginData.LoginUIEvent
import com.mofosoft.chatz.data.loginData.LoginViewModel
import com.mofosoft.chatz.myRouter.Screen


@Composable
fun LoginScreen(
     navController: NavController,
     chatViewModel: ChatViewModel
) {
     CheckIfLogedIn(navController = navController, chatViewModel = chatViewModel)

     var email by remember { mutableStateOf("") }
     var password by remember { mutableStateOf("") }
     var password_visible by remember { mutableStateOf(false) }

     val loginViewModel : LoginViewModel = viewModel()

     Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
     ) {
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
                              text = "Welcome \n\nBack",
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
                              value = email,
                              onValueChange = {
                                   email = it
                                   loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                              },
                              label = { Text(text = "Email") },
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
                              isError = !(loginViewModel.loginUiState.value.emailError)
                         )

                         Spacer(modifier = Modifier.height(10.dp))

                         //Password
                         OutlinedTextField(
                              modifier = Modifier.fillMaxWidth(.72f),
                              value = password,
                              onValueChange = {
                                   password = it
                                   loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                              },
                              label = { Text(text = "Password") },
                              maxLines = 1,
                              keyboardOptions = KeyboardOptions(
                                   imeAction = ImeAction.Done,
                                   keyboardType = KeyboardType.Password
                              ),
                              shape = RoundedCornerShape(8.dp),
                              visualTransformation = if(password_visible) VisualTransformation.None else PasswordVisualTransformation(),
                              trailingIcon = {
                                   val image = if(password_visible) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24
                                   Icon(
                                        imageVector = ImageVector.vectorResource(image),
                                        contentDescription = "Password-Icon",
                                        modifier = Modifier.clickable {
                                             password_visible = !password_visible
                                        }
                                   )
                              },
                              isError = !(loginViewModel.loginUiState.value.passwordError)
                         )

                         Spacer(modifier = Modifier.height(10.dp))

                         Button(
                              onClick = {
                                   chatViewModel.LoginUser(
                                        email = loginViewModel.loginUiState.value.email,
                                        password = loginViewModel.loginUiState.value.password
                                   )
                              },
                              colors = ButtonDefaults.buttonColors(
                                   containerColor = MaterialTheme.colorScheme.primary,
                                   contentColor = MaterialTheme.colorScheme.onPrimary
                              ),
                              enabled = loginViewModel.allValidationsPassed.value
                         ) {
                              Text(
                                   text = "Login",
                                   fontSize = 16.sp
                              )
                         }

                         Spacer(modifier = Modifier.height(10.dp))

                         Row (
                              verticalAlignment = Alignment.CenterVertically
                         ){
                              Text(text = "Don't have an account?", color = MaterialTheme.colorScheme.onPrimary)
                              TextButton(
                                   onClick = {
                                        navController.navigate(Screen.register.route)
                                   },
                                   modifier = Modifier.padding(0.dp)
                              ) {
                                   Text(text = "Register here")
                              }
                         }
                    }
               }
          }
     }
}
