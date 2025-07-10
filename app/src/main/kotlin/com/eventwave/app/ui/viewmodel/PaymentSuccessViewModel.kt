package com.eventwave.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventwave.app.data.model.Ticket
import com.eventwave.app.data.repository.TicketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PaymentSuccessUiState(
    val ticket: Ticket? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class PaymentSuccessViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(PaymentSuccessUiState())
    val uiState: StateFlow<PaymentSuccessUiState> = _uiState.asStateFlow()
    
    fun loadTicket(ticketId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                val ticket = ticketRepository.getTicketById(ticketId)
                if (ticket != null) {
                    _uiState.value = _uiState.value.copy(
                        ticket = ticket,
                        isLoading = false,
                        error = null
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Ticket not found"
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
}