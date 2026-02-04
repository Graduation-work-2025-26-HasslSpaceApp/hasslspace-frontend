package ru.hse.app.androidApp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.hse.app.androidApp.ui.components.common.box.NoItemsBox

@Composable
fun ChatsNavigation(bottomNavHostController: NavHostController) {
    val routesNavController = rememberNavController()
//
//    val createViewModel: CreateRouteViewModel = hiltViewModel()
//    val routeDetailsViewModel: RouteDetailsViewModel = hiltViewModel()
//
    NavHost(
        navController = routesNavController,
        startDestination = NavigationItem.ChatsMain.route
    ) {
        composable(NavigationItem.ChatsMain.route) {
            NoItemsBox("Чаты")
        }
    }
//        composable(NavigationItem.RoutesMain.route) {
//            RoutesScreen(routesNavController, createViewModel, routeDetailsViewModel)
//        }
//        composable(NavigationItem.Drafts.route) {
//            DraftsScreen(routesNavController, createViewModel)
//        }
//        composable(NavigationItem.RouteDetails.route + "/{routeId}") { backStackEntry ->
//            val routeId = backStackEntry.arguments?.getString("routeId")
//            if (routeId != null) {
//                RouteDetailsScreen(routesNavController, routeId, routeDetailsViewModel)
//            }
//        }
//        composable(NavigationItem.RouteReviews.route + "/{routeId}") { backStackEntry ->
//            val routeId = backStackEntry.arguments?.getString("routeId")
//            if (routeId != null) {
//                RouteReviewsScreen(routesNavController, routeId, routeDetailsViewModel)
//            }
//        }
//        composable(NavigationItem.RoutePassing.route + "/{routeId}") { backStackEntry ->
//            val routeId = backStackEntry.arguments?.getString("routeId")
//            if (routeId != null) {
//                RoutePassingScreen(
//                    bottomNavHostController,
//                    routesNavController,
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
//                RateRouteScreen(routesNavController, routeId, mark, routeDetailsViewModel)
//            }
//        }
//        composable(NavigationItem.RouteCreationInfo.route) { backStackEntry ->
//            CreateRouteInfoScreen(routesNavController, bottomNavHostController, createViewModel)
//        }
//        composable(NavigationItem.RouteCreationOnMap.route + "/{routeId}") { backStackEntry ->
//            val routeId = backStackEntry.arguments?.getString("routeId")
//            if (routeId != null) {
//                CreateRouteMapScreen(routesNavController, routeId, createViewModel)
//            }
//        }
//    }
}