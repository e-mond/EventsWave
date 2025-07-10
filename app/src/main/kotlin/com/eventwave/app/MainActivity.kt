package com.eventwave.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eventwave.app.navigation.NavigationRoutes
import com.eventwave.app.ui.screens.attendee.*
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
        // Splash Screen
        composable(NavigationRoutes.Splash.route) {
            SplashScreen(
                onNavigateToMain = {
                    navController.navigate(NavigationRoutes.AttendeeMain.route) {
                        popUpTo(NavigationRoutes.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        // Main Event List Screen
        composable(NavigationRoutes.AttendeeMain.route) {
            EventListScreen(
                navController = navController
            )
        }
        
        // Event Detail Screen
        composable(
            route = NavigationRoutes.EventDetail.route,
            arguments = listOf(
                navArgument("eventId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: ""
            EventDetailScreen(
                eventId = eventId,
                navController = navController
            )
        }
        
        // Booking Screen (Step 1)
        composable(
            route = NavigationRoutes.Booking.route,
            arguments = listOf(
                navArgument("eventId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: ""
            BookingScreen(
                eventId = eventId,
                navController = navController
            )
        }
        
        // Payment Screen (Step 2)
        composable(
            route = NavigationRoutes.Payment.route,
            arguments = listOf(
                navArgument("eventId") { type = NavType.StringType },
                navArgument("quantity") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: ""
            val quantity = backStackEntry.arguments?.getInt("quantity") ?: 1
            PaymentScreen(
                eventId = eventId,
                quantity = quantity,
                navController = navController
            )
        }
        
        // Payment Success Screen (Step 3)
        composable(
            route = NavigationRoutes.PaymentSuccess.route,
            arguments = listOf(
                navArgument("ticketId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val ticketId = backStackEntry.arguments?.getString("ticketId") ?: ""
            PaymentSuccessScreen(
                ticketId = ticketId,
                navController = navController
            )
        }
        
        // TODO: Add more screens as they are implemented
        // - UserTickets screen
        // - TicketDetail screen
        // - Profile screen
        // - Organizer screens
    }
}