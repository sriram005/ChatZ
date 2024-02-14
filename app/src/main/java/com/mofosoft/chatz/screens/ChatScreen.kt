package com.mofosoft.chatz.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Send
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mofosoft.chatz.ChatViewModel

@Composable
fun ChatScreen(
    navController: NavController,
    chatViewModel: ChatViewModel,
    chatId : String
) {
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
                        .background(color = MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        },
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "back"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "User",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },

        ) {
            ChatView(
                paddingValues = it,
                chatViewModel = chatViewModel,
                chatId = chatId
            )
        }
    }
}

@Composable
fun ChatView(
    paddingValues: PaddingValues,
    chatViewModel: ChatViewModel,
    chatId: String
) {
    var message by remember { mutableStateOf("") }
    val sendMessage = {
        chatViewModel.onSendMessage(chatId, message)
        message = ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ){
            items(50){
                Text(text = "HI")
            }
        }
        SendBox(message = message, onMessageChanged = {
            message = it
        }) {

        }
    }
}

@Composable
fun SendBox(
    message : String,
    onMessageChanged : (String) -> Unit,
    onSend : () -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = message,
            onValueChange = onMessageChanged,
            placeholder = {
                Text(text = "Enter your message")
            },
            shape = RoundedCornerShape(35.dp)
        )
        IconButton(
            modifier = Modifier.background(color = Color.Transparent),
            onClick = { onSend.invoke() }
        ) {
            Icon(
                imageVector = Icons.Rounded.Send,
                contentDescription = "send",
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .clip(CircleShape)
                    .padding(8.dp),
                tint = Color.White
            )
        }
    }
}