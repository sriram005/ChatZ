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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Person
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mofosoft.chatz.ChatViewModel
import com.mofosoft.chatz.CommomImage
import com.mofosoft.chatz.CommonProgressBar

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
                        text = "Update Profile",
                        fontSize = 19.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    IconButton(
                        onClick = {

                        }) {
                        Icon(
                            imageVector = Icons.Rounded.ExitToApp,
                            contentDescription = "profile",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        ) {
            if (inProgress)
                CommonProgressBar()
            else
                EditScreen(
                    paddingValues = it,
                    chatViewModel = chatViewModel,
                    name = "",
                    number = "",
                    onNameChanged = {},
                    onNumberChanged = {},
                    onSave = {}
                )
        }
    }
}

@Composable
fun EditScreen(
    paddingValues: PaddingValues,
    chatViewModel: ChatViewModel,
    name : String,
    number: String,
    onNameChanged : (String) -> Unit,
    onNumberChanged : (String) -> Unit,
    onSave : () -> Unit
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
                imageVector = Icons.Rounded.ExitToApp,
                contentDescription = "Save Changes"
            )
            Text(
                text = "Logout",
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun ProfileImage(
    imageUrl : String?,
    chatViewModel: ChatViewModel
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){uri->
        uri?.let{
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
            CommomImage(data = imageUrl)
        }
        Text(text = "Change profile Photo")
    }
    if(chatViewModel.inProgress.value)
        CommonProgressBar()
}
