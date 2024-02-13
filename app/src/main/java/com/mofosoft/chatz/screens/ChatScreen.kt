package com.mofosoft.chatz.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mofosoft.chatz.ChatViewModel

@Composable
fun ChatScreen(
    navController: NavController,
    chatViewModel: ChatViewModel,
    chatId : String
) {
    var message by remember { mutableStateOf("") }
    SendBox(message = message, onMessageChanged = {
        message = it
    }) {

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
            ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = message,
            onValueChange = onMessageChanged,
            label = {
                Text(text = "Enter your message")
            }
        )
        IconButton(
            onClick = { onSend.invoke() }
        ) {
            Icon(
                imageVector = Icons.Rounded.Send,
                contentDescription = "send",
                modifier = Modifier
                    .background(Color.DarkGray)
                    .clip(CircleShape)
                    .padding(8.dp),
                tint = Color.White
            )
        }
    }
}