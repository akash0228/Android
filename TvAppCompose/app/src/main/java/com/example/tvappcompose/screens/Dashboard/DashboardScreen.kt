package com.example.tvappcompose.screens.Dashboard

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tvappcompose.screens.All.AllScreen
import com.example.tvappcompose.screens.recent.RecentScreen
import com.example.tvappcompose.screens.search.SearchScreen
import com.example.tvappcompose.screens.watchlater.WatchLaterScreen
import kotlinx.coroutines.delay

@Composable
fun DashboardScreen(
    openDetailScreen:(showId:Int) -> Unit
){
    val focusManager = LocalFocusManager.current
    val navController = rememberNavController()
    var isTopBarFocused by remember { mutableStateOf(false) }

    var currentDestination: String? by remember { mutableStateOf(null) }
    val currentTopBarSelectedTabIndex by remember(currentDestination) {
        derivedStateOf {
            currentDestination?.let { tabs.indexOf(it) } ?: 0
        }
    }

    DisposableEffect(Unit) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            currentDestination = destination.route
        }

        navController.addOnDestinationChangedListener(listener)

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }

    var wasTopBarFocusRequestedBefore by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!wasTopBarFocusRequestedBefore) {
            TopBarFocusRequesters[currentTopBarSelectedTabIndex].requestFocus()
            wasTopBarFocusRequestedBefore = true
        }
    }

    Box(modifier = Modifier.focusGroup()){
        DashboardTopBar(selectedTabIndex = currentTopBarSelectedTabIndex, modifier = Modifier.zIndex(10f)){
            navController.navigate(it){
                launchSingleTop = true
                restoreState = true
            }
        }
        Body(
            modifier= Modifier
                .zIndex(1f)
                .onFocusChanged {
                    if (it.hasFocus) {
                        Log.d("MYAPP", "Body: Has Focus")
                    }
                },
            navController=navController,
            openDetailScreen=openDetailScreen
        )
    }

}

@Composable
fun Body(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openDetailScreen: (showId: Int) -> Unit
){
    NavHost(navController = navController, startDestination = "All") {

        composable("All"){
            AllScreen(
                onShowClick = { selectedShow ->
                    Log.d("MYAPP", "Body: Open Detail Screen")
                    openDetailScreen(selectedShow.id)
                },
                modifier = modifier
            )
        }

        composable("Recent"){
            RecentScreen(
                onShowClick = { selectedShow ->
                    openDetailScreen(selectedShow.id)
                }
            )
        }

        composable("WatchLater"){
            WatchLaterScreen(
                onShowClick = { selectedShow ->
                    openDetailScreen(selectedShow.id)
                }
            )
        }

        composable("Search"){
            SearchScreen(
                onShowClick = { selectedShow ->
                    openDetailScreen(selectedShow.id)
                }
            )
        }
    }
}