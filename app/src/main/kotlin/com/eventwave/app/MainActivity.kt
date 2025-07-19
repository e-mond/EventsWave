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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.eventwave.app.navigation.NavigationRoutes
import com.eventwave.app.ui.screens.attendee.EventListScreen
import com.eventwave.app.ui.screens.attendee.EventDetailScreen
import com.eventwave.app.ui.screens.auth.LoginScreen
import com.eventwave.app.ui.screens.auth.SignUpScreen
import com.eventwave.app.ui.screens.splash.SplashScreen
import com.eventwave.app.ui.theme.EventWaveTheme
import com.eventwave.app.ui.viewmodel.LoginViewModel
import com.eventwave.app.ui.viewmodel.MainViewModel
import com.eventwave.app.ui.viewmodel.SignUpViewModel
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
    val loginViewModel: LoginViewModel = hiltViewModel()
    val signUpViewModel: SignUpViewModel = hiltViewModel()
    
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
                    navController.navigate(NavigationRoutes.Login.route) {
                        popUpTo(NavigationRoutes.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(NavigationRoutes.Login.route) {
            LoginScreen(
                onLoginClick = { emailOrPhone, password ->
                    loginViewModel.login(emailOrPhone, password)
                },
                onForgotPasswordClick = {
                    // TODO: Navigate to forgot password screen
                },
                onSignUpClick = {
                    navController.navigate(NavigationRoutes.SignUp.route)
                },
                onHelpClick = {
                    // TODO: Show help dialog
                }
            )
        }
        
        composable(NavigationRoutes.SignUp.route) {
            SignUpScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSignUpClick = { fullName, emailOrPhone, password, confirmPassword, role ->
                    signUpViewModel.signUp(fullName, emailOrPhone, password, confirmPassword, role)
                },
                onSignInClick = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(NavigationRoutes.AttendeeMain.route) {
            EventListScreen(
                navController = navController
            )
        }
        
        composable(
            route = NavigationRoutes.EventDetail.route,
            arguments = listOf(
                navArgument("eventId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: ""
            EventDetailScreen(
                eventId = eventId,
                navController = navController
            )
        }
        
        // Add more navigation destinations here as we build them
    }
    
    // Handle login state changes
    LaunchedEffect(loginViewModel.loginState.collectAsState().value) {
        val loginState = loginViewModel.loginState.value
        if (loginState.isLoggedIn) {
            navController.navigate(NavigationRoutes.AttendeeMain.route) {
                popUpTo(NavigationRoutes.Login.route) { inclusive = true }
            }
            loginViewModel.resetLoginState()
        }
    }
    
    // Handle signup state changes
    LaunchedEffect(signUpViewModel.signUpState.collectAsState().value) {
        val signUpState = signUpViewModel.signUpState.value
        if (signUpState.isSignedUp) {
            navController.navigate(NavigationRoutes.AttendeeMain.route) {
                popUpTo(NavigationRoutes.SignUp.route) { inclusive = true }
            }
            signUpViewModel.resetSignUpState()
        }
    }
}