package com.eventwave.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventwave.app.data.model.Event
import com.eventwave.app.data.model.PaymentMethod
import com.eventwave.app.data.model.Ticket
import com.eventwave.app.data.model.User
import com.eventwave.app.data.repository.EventRepository
import com.eventwave.app.data.repository.TicketRepository
import com.eventwave.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PaymentUiState(
    val event: Event? = null,
    val user: User? = null,
    val quantity: Int = 1,
    val totalPrice: Double = 0.0,
    val selectedPaymentMethod: PaymentMethod = PaymentMethod.CARD,
    val isLoading: Boolean = false,
    val isProcessingPayment: Boolean = false,
    val error: String? = null,
    val paymentSuccess: Boolean = false,
    val createdTicket: Ticket? = null
)

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val userRepository: UserRepository,
    private val ticketRepository: TicketRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState.asStateFlow()
    
    fun loadPaymentData(eventId: String, quantity: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                val event = eventRepository.getEventById(eventId)
                val user = userRepository.getCurrentUser()
                
                if (event != null && user != null) {
                    _uiState.value = _uiState.value.copy(
                        event = event,
                        user = user,
                        quantity = quantity,
                        totalPrice = event.ticketPrice * quantity,
                        isLoading = false,
                        error = null
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = if (event == null) "Event not found" else "User not found"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }
    
    fun selectPaymentMethod(paymentMethod: PaymentMethod) {
        _uiState.value = _uiState.value.copy(selectedPaymentMethod = paymentMethod)
    }
    
    fun processPayment() {
        val currentState = _uiState.value
        val event = currentState.event
        val user = currentState.user
        
        if (event == null || user == null) {
            _uiState.value = currentState.copy(error = "Missing event or user data")
            return
        }
        
        viewModelScope.launch {
            _uiState.value = currentState.copy(
                isProcessingPayment = true,
                error = null
            )
            
            try {
                val result = ticketRepository.bookTicket(
                    event = event,
                    user = user,
                    quantity = currentState.quantity,
                    paymentMethod = currentState.selectedPaymentMethod
                )
                
                result.fold(
                    onSuccess = { ticket ->
                        _uiState.value = _uiState.value.copy(
                            isProcessingPayment = false,
                            paymentSuccess = true,
                            createdTicket = ticket,
                            error = null
                        )
                    },
                    onFailure = { exception ->
                        _uiState.value = _uiState.value.copy(
                            isProcessingPayment = false,
                            error = exception.message ?: "Payment failed"
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isProcessingPayment = false,
                    error = e.message ?: "Payment processing failed"
                )
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}