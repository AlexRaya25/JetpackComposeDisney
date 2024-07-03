package com.rayadev.jetpackcomposedisney.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rayadev.jetpackcomposedisney.presentation.screens.detail.DetailScreen
import com.rayadev.jetpackcomposedisney.presentation.screens.main.MainScreen
import com.rayadev.jetpackcomposedisney.presentation.screens.search.SearchScreen
import com.rayadev.jetpackcomposedisney.presentation.screens.searchResult.SearchResultScreen
import com.rayadev.jetpackcomposedisney.utils.Constants.DESTINATION_DETAIL
import com.rayadev.jetpackcomposedisney.utils.Constants.DESTINATION_MAIN
import com.rayadev.jetpackcomposedisney.utils.Constants.DESTINATION_SEARCH
import com.rayadev.jetpackcomposedisney.utils.Constants.DESTINATION_SEARCH_RESULT
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = DESTINATION_MAIN) {
        addPlayersScreen(navController)
        addDetailScreen(navController)
        addSearchScreen(navController)
        addSearchResultScreen(navController)
    }
}

private fun NavGraphBuilder.addPlayersScreen(navController: NavController) {
    composable(
        route = DESTINATION_MAIN
    ) {
        MainScreen(navController)
    }
}

private fun NavGraphBuilder.addDetailScreen(navController: NavController) {
    composable(
        route = DESTINATION_DETAIL + "/{id}",
        arguments = listOf(navArgument("id") { type = NavType.IntType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val id = arguments.getInt("id")
        DetailScreen(
            id = id,
            onUpClick = { navController.popBackStack() }
        )
    }
}

private fun NavGraphBuilder.addSearchScreen(navController: NavController) {
    composable(
        route = DESTINATION_SEARCH
    ) {
        val coroutineScope = rememberCoroutineScope()
        val navigateToSearchResults: (String) -> Unit = { query ->
            coroutineScope.launch {
                navController.navigate(DESTINATION_SEARCH_RESULT + "/$query")
            }
        }
        SearchScreen(navController = navController, onSearchSubmitted = navigateToSearchResults)
    }
}

private fun NavGraphBuilder.addSearchResultScreen(navController: NavController) {
    composable(
        route = DESTINATION_SEARCH_RESULT + "/{query}",
        arguments = listOf(navArgument("query") { type = NavType.StringType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val query = arguments.getString("query") ?: ""
        SearchResultScreen(navController, searchQuery = query)
    }
}
