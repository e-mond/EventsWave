package com.eventwave.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val error: String? = null
)

class LoginViewModel : ViewModel() {
    
    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()
    
    fun login(emailOrPhone: String, password: String) {
        if (emailOrPhone.isBlank() || password.isBlank()) {
            _loginState.value = _loginState.value.copy(
                error = "Please fill in all fields"
            )
            return
        }
        
        viewModelScope.launch {
            _loginState.value = _loginState.value.copy(
                isLoading = true,
                error = null
            )
            
            try {
                // Simulate API call
                kotlinx.coroutines.delay(1000)
                
                // For demo purposes, accept any non-empty credentials
                if (emailOrPhone.isNotBlank() && password.isNotBlank()) {
                    _loginState.value = _loginState.value.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        error = null
                    )
                } else {
                    _loginState.value = _loginState.value.copy(
                        isLoading = false,
                        error = "Invalid credentials"
                    )
                }
            } catch (e: Exception) {
                _loginState.value = _loginState.value.copy(
                    isLoading = false,
                    error = "Login failed: ${e.message}"
                )
            }
        }
    }
    
    fun clearError() {
        _loginState.value = _loginState.value.copy(error = null)
    }
    
    fun resetLoginState() {
        _loginState.value = LoginState()
    }
} 