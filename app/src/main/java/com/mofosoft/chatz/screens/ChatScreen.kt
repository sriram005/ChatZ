package com.mofosoft.chatz.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mofosoft.chatz.ChatViewModel
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
            }
        ) {
            ChatList(it)
        }
    }
}

@Composable
fun ChatList(paddingValues: PaddingValues) {

}
