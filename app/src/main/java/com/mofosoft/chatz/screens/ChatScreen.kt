package com.mofosoft.chatz.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mofosoft.chatz.ChatViewModel
import com.mofosoft.chatz.CommonProgressBar
import com.mofosoft.chatz.myRouter.Screen

@Composable
fun ChatScreen(
    navController: NavController,
    chatViewModel: ChatViewModel
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val showDialog = remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(color = MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterStart),
                        text = "ChatZ",
                        fontSize = 19.sp,
                        color = MaterialTheme.colorScheme.onPrimary,

                    )
                    IconButton(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        onClick = {
                            navController.navigate(Screen.profile.route)
                        }) {
                        Icon(
                            modifier = Modifier.align(Alignment.Center),
                            imageVector = Icons.Rounded.Person,
                            contentDescription = "profile",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                }
            },

            floatingActionButton = {
                FloatingActionButton(
                    shape = CircleShape,
                    onClick = {
                        showDialog.value = true
                    }
                ) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add chat")
                }
            }
        ) {
            ChatList(
                paddingValues = it,
                showDialog = showDialog,
                chatViewModel = chatViewModel
            )
        }
    }
}

@Composable
fun ChatList(
    paddingValues: PaddingValues,
    showDialog : MutableState<Boolean>,
    chatViewModel: ChatViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        val inProgress = chatViewModel.inProcessChats
        if(inProgress.value){
            CommonProgressBar()
        }
        else{
            val chats = chatViewModel.chats.value
            val userData = chatViewModel.userData.value

            val onDissmiss : () -> Unit = { showDialog.value = false}
            val onAddChat : (String) -> Unit = {
                chatViewModel.onAddChat(it)
                showDialog.value = false
            }

            if (chats.isEmpty()){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No Chats Available"
                    )
                }
            }
            
            DialogBox(
                showDialog = showDialog.value,
                onDissmiss = onDissmiss,
                onAddChat = onAddChat
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogBox(
    showDialog : Boolean,
    onDissmiss : () -> Unit,
    onAddChat : (String) -> Unit
) {
    val addChatNumber = remember { mutableStateOf("") }

    if(showDialog){
        AlertDialog(
            onDismissRequest = {
                onDissmiss.invoke()
            },
            confirmButton = {
                Button(onClick = {
                    onAddChat(addChatNumber.value)
                }) {
                    Text(text = "Add Chat")
                }
            },
            title = {
                Text(text = "Add Chat")
            },
            text = {
                OutlinedTextField(
                    value = addChatNumber.value,
                    onValueChange = {
                        addChatNumber.value = it
                    },
                    placeholder = {
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
                    },
                    isError = addChatNumber.value.length!=10
                )
            }
        )
    }
}