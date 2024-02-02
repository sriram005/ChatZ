package com.mofosoft.chatz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.mofosoft.chatz.myRouter.Screen

@Composable
fun CommonProgressBar() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.5f)
            .background(color = Color.LightGray),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}
@Composable
fun CheckIfRegistered(
    navController: NavController,
    chatViewModel: ChatViewModel
){
    val alreadyRegistered = remember { mutableStateOf(false) }
    val registered = chatViewModel.signIn.value
    if(registered && !alreadyRegistered.value){
        alreadyRegistered.value = true
        navController.navigate(Screen.login.route){
            popUpTo(0)
        }
    }
}

@Composable
fun CheckIfLogedIn(
    navController: NavController,
    chatViewModel: ChatViewModel
) {
    val alreadyLogedIn = remember{ mutableStateOf(false) }
    val logedIn = chatViewModel.logIn.value
    if(logedIn && !alreadyLogedIn.value){
        alreadyLogedIn.value = true
        navController.navigate(Screen.chat.route){
            popUpTo(0)
        }
    }
}

@Composable
fun CommomImage(
    data : String?,
    modifier: Modifier = Modifier.wrapContentSize(),
    contentScale: ContentScale = ContentScale.Crop
) {
    val painter = rememberImagePainter(data = data)
    Image(
        painter = painter,
        contentDescription = null,
        contentScale = contentScale,
        modifier = modifier
    )
}