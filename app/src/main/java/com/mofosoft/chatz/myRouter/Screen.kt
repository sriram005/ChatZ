package com.mofosoft.chatz.myRouter

sealed class Screen(var route : String) {
    object login : Screen("login")
    object register : Screen("register")
    object profile : Screen("profile")
    object chatList : Screen("chatList")
    object chat : Screen("chat/{chatId}"){
        fun createRoute(id : String) = "chat/$id"
    }

}