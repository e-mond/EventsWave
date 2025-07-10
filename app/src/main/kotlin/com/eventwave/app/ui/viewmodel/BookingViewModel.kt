package com.eventwave.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventwave.app.data.model.Event
import com.eventwave.app.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BookingUiState(
    val event: Event? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedQuantity: Int = 1,
    val totalPrice: Double = 0.0,
    val maxQuantity: Int = 10
)

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(BookingUiState())
    val uiState: StateFlow<BookingUiState> = _uiState.asStateFlow()
    
    fun loadEvent(eventId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                val event = eventRepository.getEventById(eventId)
                if (event != null) {
                    val maxQuantity = minOf(event.availableTickets, 10)
                    val quantity = minOf(_uiState.value.selectedQuantity, maxQuantity)
                    
                    _uiState.value = _uiState.value.copy(
                        event = event,
                        isLoading = false,
                        selectedQuantity = quantity,
                        totalPrice = event.ticketPrice * quantity,
                        maxQuantity = maxQuantity,
                        error = null
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Event not found"
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
    
    fun updateQuantity(quantity: Int) {
        val currentState = _uiState.value
        if (quantity in 1..currentState.maxQuantity) {
            _uiState.value = currentState.copy(
                selectedQuantity = quantity,
                totalPrice = (currentState.event?.ticketPrice ?: 0.0) * quantity
            )
        }
    }
    
    fun incrementQuantity() {
        val currentState = _uiState.value
        if (currentState.selectedQuantity < currentState.maxQuantity) {
            updateQuantity(currentState.selectedQuantity + 1)
        }
    }
    
    fun decrementQuantity() {
        val currentState = _uiState.value
        if (currentState.selectedQuantity > 1) {
            updateQuantity(currentState.selectedQuantity - 1)
        }
    }
}