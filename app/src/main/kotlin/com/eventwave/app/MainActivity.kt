package com.eventwave.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eventwave.app.navigation.NavigationRoutes
import com.eventwave.app.ui.screens.attendee.EventListScreen
import com.eventwave.app.ui.screens.splash.SplashScreen
import com.eventwave.app.ui.theme.EventWaveTheme
import com.eventwave.app.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventWaveTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EventWaveApp()
                }
            }
        }
    }
}

@Composable
fun EventWaveApp() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = hiltViewModel()
    
    // Initialize sample data
    LaunchedEffect(Unit) {
        mainViewModel.initializeSampleData()
    }
    
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Splash.route
    ) {
        composable(NavigationRoutes.Splash.route) {
            SplashScreen(
                onNavigateToMain = {
                    navController.navigate(NavigationRoutes.AttendeeMain.route) {
                        popUpTo(NavigationRoutes.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(NavigationRoutes.AttendeeMain.route) {
            EventListScreen(
                navController = navController
            )
        }
        
        // Add more navigation destinations here as we build them
    }
}