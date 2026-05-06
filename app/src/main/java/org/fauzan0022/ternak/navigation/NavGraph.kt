package org.fauzan0022.ternak.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.fauzan0022.ternak.ui.screen.DetailScreen
import org.fauzan0022.ternak.ui.screen.MainScreen
import org.fauzan0022.ternak.ui.screen.KesehatanScreen
import org.fauzan0022.ternak.ui.screen.RecycleScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.FormBaru.route) {
            DetailScreen(navController)
        }
        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_ID) { type = NavType.LongType }
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID)
            DetailScreen(navController, id)
        }
        composable(
            route = Screen.Kesehatan.route,
            arguments = listOf(
                navArgument(KEY_ID) { type = NavType.LongType }
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID)
            KesehatanScreen(navController, id)
        }
        composable(route = Screen.Recycle.route) {
            RecycleScreen(navController)
        }
    }
}