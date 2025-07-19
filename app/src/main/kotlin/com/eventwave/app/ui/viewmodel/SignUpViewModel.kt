package com.eventwave.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventwave.app.ui.screens.auth.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SignUpState(
    val isLoading: Boolean = false,
    val isSignedUp: Boolean = false,
    val error: String? = null
)

class SignUpViewModel : ViewModel() {
    
    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState: StateFlow<SignUpState> = _signUpState.asStateFlow()
    
    fun signUp(
        fullName: String,
        emailOrPhone: String,
        password: String,
        confirmPassword: String,
        role: UserRole
    ) {
        // Validation
        when {
            fullName.isBlank() -> {
                _signUpState.value = _signUpState.value.copy(
                    error = "Please enter your full name"
                )
                return
            }
            emailOrPhone.isBlank() -> {
                _signUpState.value = _signUpState.value.copy(
                    error = "Please enter your email or phone number"
                )
                return
            }
            password.isBlank() -> {
                _signUpState.value = _signUpState.value.copy(
                    error = "Please enter a password"
                )
                return
            }
            password.length < 6 -> {
                _signUpState.value = _signUpState.value.copy(
                    error = "Password must be at least 6 characters long"
                )
                return
            }
            confirmPassword.isBlank() -> {
                _signUpState.value = _signUpState.value.copy(
                    error = "Please confirm your password"
                )
                return
            }
            password != confirmPassword -> {
                _signUpState.value = _signUpState.value.copy(
                    error = "Passwords do not match"
                )
                return
            }
        }
        
        viewModelScope.launch {
            _signUpState.value = _signUpState.value.copy(
                isLoading = true,
                error = null
            )
            
            try {
                // Simulate API call
                kotlinx.coroutines.delay(1500)
                
                // For demo purposes, accept any valid input
                _signUpState.value = _signUpState.value.copy(
                    isLoading = false,
                    isSignedUp = true,
                    error = null
                )
            } catch (e: Exception) {
                _signUpState.value = _signUpState.value.copy(
                    isLoading = false,
                    error = "Sign up failed: ${e.message}"
                )
            }
        }
    }
    
    fun clearError() {
        _signUpState.value = _signUpState.value.copy(error = null)
    }
    
    fun resetSignUpState() {
        _signUpState.value = SignUpState()
    }
} 