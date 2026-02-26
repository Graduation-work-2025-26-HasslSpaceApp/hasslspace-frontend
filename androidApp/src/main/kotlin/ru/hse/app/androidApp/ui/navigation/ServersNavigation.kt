package ru.hse.app.androidApp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.hse.app.androidApp.screen.servers.CreateServerScreen
import ru.hse.app.androidApp.screen.servers.ServersScreen
import ru.hse.app.androidApp.screen.servers.JoinServerScreen
import ru.hse.app.androidApp.ui.components.common.box.NoItemsBox

@Composable
fun ServersNavigation(bottomNavHostController: NavHostController) {
    val serversNavController = rememberNavController()

    NavHost(
        navController = serversNavController,
        startDestination = NavigationItem.ServersMain.route
    ) {
        composable(NavigationItem.ServersMain.route) {
            ServersScreen(serversNavController)
        }

        composable(NavigationItem.CreateServer.route) {
            CreateServerScreen(serversNavController)
        }

        composable(NavigationItem.JoinServer.route) {
            JoinServerScreen(serversNavController)
        }
    }
//    composable(NavigationItem.HomeMain.route) {
//        HomeScreen(homeNavController, routeDetailsViewModel)
//    }
//        composable(NavigationItem.RouteDetails.route + "/{routeId}") { backStackEntry ->
//            val routeId = backStackEntry.arguments?.getString("routeId")
//            if (routeId != null) {
//                RouteDetailsScreen(homeNavController, routeId, routeDetailsViewModel)
//            }
//        }
//        composable(NavigationItem.RouteReviews.route + "/{routeId}") { backStackEntry ->
//            val routeId = backStackEntry.arguments?.getString("routeId")
//            if (routeId != null) {
//                RouteReviewsScreen(homeNavController, routeId, routeDetailsViewModel)
//            }
//        }
//
//        composable(NavigationItem.RoutePassing.route + "/{routeId}") { backStackEntry ->
//            val routeId = backStackEntry.arguments?.getString("routeId")
//            if (routeId != null) {
//                RoutePassingScreen(
//                    bottomNavHostController,
//                    homeNavController,
//                    routeId,
//                    routeDetailsViewModel
//                )
//            }
//        }
//        composable(
//            route = NavigationItem.RouteRate.route + "/{routeId}/{averageMark}",
//            arguments = listOf(
//                navArgument(name = "routeId") {
//                    type = NavType.StringType
//                },
//                navArgument(name = "averageMark") {
//                    type = NavType.IntType
//                },
//            )
//        ) { backStackEntry ->
//            val routeId = backStackEntry.arguments?.getString("routeId")
//            val mark = backStackEntry.arguments?.getInt("averageMark")
//            if (routeId != null && mark != null) {
//                RateRouteScreen(homeNavController, routeId, mark, routeDetailsViewModel)
//            }
//        }
//    }
}