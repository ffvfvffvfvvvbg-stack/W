package com.telegram2.messaging.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.telegram2.messaging.ui.screens.ChatListScreen
import com.telegram2.messaging.ui.screens.ChatScreen
import com.telegram2.messaging.ui.screens.LoginScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(onLoginSuccess = { navController.navigate("chats") }) }
        composable("chats") { ChatListScreen(onChatClick = { chatId -> navController.navigate("chat/$chatId") }) }
        composable("chat/{chatId}") { ChatScreen() }
    }
}