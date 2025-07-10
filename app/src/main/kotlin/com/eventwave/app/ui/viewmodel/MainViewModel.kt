package com.eventwave.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventwave.app.data.repository.EventRepository
import com.eventwave.app.data.repository.TicketRepository
import com.eventwave.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val userRepository: UserRepository,
    private val ticketRepository: TicketRepository
) : ViewModel() {
    
    fun initializeSampleData() {
        viewModelScope.launch {
            try {
                // Initialize sample data in order: users -> events -> tickets
                userRepository.initializeSampleData()
                eventRepository.initializeSampleData()
                ticketRepository.initializeSampleData()
            } catch (e: Exception) {
                // Handle initialization error
                e.printStackTrace()
            }
        }
    }
}