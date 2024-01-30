package com.mofosoft.chatz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.mofosoft.chatz.ui.theme.ChatZTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.mofosoft.chatz.myRouter.Screen
import com.mofosoft.chatz.screens.ChatScreen
import com.mofosoft.chatz.screens.LoginScreen
import com.mofosoft.chatz.screens.ProfileScreen
import com.mofosoft.chatz.screens.RegisterScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatZTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChatAppNavigation()
                }
            }
        }
    }

    @Composable
    fun ChatAppNavigation(){
        val navController = rememberNavController()

        var chatViewModel = hiltViewModel<ChatViewModel>()

        NavHost(
            navController = navController,
            startDestination = Screen.register.route
        ){
            composable(
                route = Screen.login.route
            ){
                LoginScreen(
                    navController = navController,
                    chatViewModel = chatViewModel
                )
            }

            composable(
                route = Screen.register.route
            ){
                RegisterScreen(
                    navController = navController,
                    chatViewModel = chatViewModel
                )
            }

            composable(
                route = Screen.profile.route
            ){
                ProfileScreen(navController)
            }

            composable(
                route = Screen.chat.route
            ){
                ChatScreen(navController)
            }
        }
    }
}
