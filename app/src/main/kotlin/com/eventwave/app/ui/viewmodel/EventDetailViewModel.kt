package com.eventwave.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventwave.app.data.model.Event
import com.eventwave.app.data.repository.EventRepository
import com.eventwave.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EventDetailUiState(
    val event: Event? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isBookingEnabled: Boolean = false,
    val userHasTicket: Boolean = false
)

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(EventDetailUiState())
    val uiState: StateFlow<EventDetailUiState> = _uiState.asStateFlow()
    
    fun loadEvent(eventId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                val event = eventRepository.getEventById(eventId)
                if (event != null) {
                    _uiState.value = _uiState.value.copy(
                        event = event,
                        isLoading = false,
                        isBookingEnabled = event.availableTickets > 0,
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
    
    fun refreshEvent() {
        _uiState.value.event?.let { event ->
            loadEvent(event.id)
        }
    }
}