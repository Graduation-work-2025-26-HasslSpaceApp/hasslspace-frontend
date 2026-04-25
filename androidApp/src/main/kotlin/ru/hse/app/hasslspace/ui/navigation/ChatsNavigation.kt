//package ru.hse.app.androidApp.ui.navigation
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import ru.hse.app.androidApp.screen.chats.ChatScreen
//import ru.hse.app.androidApp.screen.chats.ChatsScreen
//
//@Composable
//fun ChatsNavigation(bottomNavHostController: NavHostController) {
//    NavHost(
//        navController = bottomNavHostController,
//        startDestination = NavigationItem.ChatsMain.route
//    ) {
//        composable(NavigationItem.ChatsMain.route) {
//            ChatsScreen(bottomNavHostController)
//        }
//
//        composable(NavigationItem.Chat.route + "/{chatId}") { backStackEntry ->
//            val chatId = backStackEntry.arguments?.getString("chatId")
//            if (chatId != null) {
//                ChatScreen(chatId, bottomNavHostController)
//            }
//        }
//    }
//}