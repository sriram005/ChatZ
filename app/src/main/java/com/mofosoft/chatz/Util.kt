package com.mofosoft.chatz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        navController.navigate(Screen.chatList.route){
            popUpTo(0)
        }
    }
}

@Composable
fun CommonImage(
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

@Composable
fun TitleText(txt : String) {
    Text(
        text = txt,
        fontWeight = FontWeight.Bold,
        fontSize = 35.sp,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun CommonRow(
    image : String?,
    name : String?,
    onItemClick : () -> Unit
) {
    Card (
        modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .clickable {
                    onItemClick.invoke()
                },
            verticalAlignment = Alignment.CenterVertically
        ){
            CommonImage(
                data = image,
                modifier = Modifier
                    .padding(8.dp)
                    .size(50.dp)
                    .clip(shape = CircleShape)
                    .background(MaterialTheme.colorScheme.secondary)
            )
            Text(
                text = name ?: "-----",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}