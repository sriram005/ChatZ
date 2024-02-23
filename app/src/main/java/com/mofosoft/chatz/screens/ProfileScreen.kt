package com.mofosoft.chatz.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mofosoft.chatz.ChatViewModel
import com.mofosoft.chatz.CommonImage
import com.mofosoft.chatz.CommonProgressBar
import com.mofosoft.chatz.myRouter.Screen

@Composable
fun ProfileScreen(
    navController: NavController,
    chatViewModel: ChatViewModel
) {
    val inProgress = chatViewModel.inProgress.value

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(color = MaterialTheme.colorScheme.primary),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "profile",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Profile",
                        fontSize = 19.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    IconButton(
                        onClick = {
                            chatViewModel.logout()
                            navController.navigate(Screen.login.route)
                        }) {
                        Icon(
                            imageVector = Icons.Rounded.ExitToApp,
                            contentDescription = "logout",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        ) {
            if (inProgress)
                CommonProgressBar()
            else {

                val userdata = chatViewModel.userData.value
                var name by rememberSaveable { mutableStateOf(userdata?.name?:"") }
                var number by rememberSaveable { mutableStateOf(userdata?.number?:"") }
                EditScreen(
                    paddingValues = it,
                    chatViewModel = chatViewModel,
                    name = name,
                    number = number,
                    onNameChanged = { name  = it},
                    onNumberChanged = { number = it},
                    onSave = {
                        chatViewModel.createOrUpdateProfile(
                            name = name,
                            number = number,
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun EditScreen(
    paddingValues: PaddingValues,
    chatViewModel: ChatViewModel,
    name: String,
    number: String,
    onNameChanged: (String) -> Unit,
    onNumberChanged: (String) -> Unit,
    onSave: () -> Unit
) {
    val imageUrl = chatViewModel.userData.value?.imageUrl

    Column(
        modifier = Modifier
            .padding(paddingValues.calculateTopPadding())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ProfileImage(
            imageUrl = imageUrl,
            chatViewModel = chatViewModel
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(.8f),
            value = name,
            onValueChange = onNameChanged,
            label = {
                Text(text = "Name")
            },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Person-icon"
                )
            },
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(.8f),
            value = number,
            onValueChange = onNumberChanged,
            label = {
                Text(text = "Number")
            },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Call,
                    contentDescription = "Phone-icon"
                )
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                onSave.invoke()
            }
        ) {
            Icon(
                modifier = Modifier.padding(end = 6.dp),
                imageVector = Icons.Rounded.Done,
                contentDescription = "Save Changes",
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = "Save Changes",
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun ProfileImage(
    imageUrl: String?,
    chatViewModel: ChatViewModel
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            chatViewModel.uploadProfileImage(uri)
        }
    }

    Column(
        modifier = Modifier
            .size(200.dp)
            .padding(8.dp)
            .clickable {
                launcher.launch("image/*")
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            shape = CircleShape,
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp)
        ) {
            CommonImage(data = imageUrl)
        }
        Text(
            text = "Change profile Photo",
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
    if (chatViewModel.inProgress.value)
        CommonProgressBar()
}
