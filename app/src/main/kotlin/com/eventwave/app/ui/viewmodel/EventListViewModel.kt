package com.eventwave.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventwave.app.data.model.Event
import com.eventwave.app.data.model.EventCategory
import com.eventwave.app.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EventListUiState(
    val events: List<Event> = emptyList(),
    val filteredEvents: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val selectedCategory: EventCategory? = null,
    val selectedCity: String = ""
)

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(EventListUiState())
    val uiState: StateFlow<EventListUiState> = _uiState.asStateFlow()
    
    private val _searchQuery = MutableStateFlow("")
    private val _selectedCategory = MutableStateFlow<EventCategory?>(null)
    private val _selectedCity = MutableStateFlow("")
    
    init {
        observeEvents()
        observeFilters()
    }
    
    private fun observeEvents() {
        viewModelScope.launch {
            eventRepository.getAllActiveEvents()
                .catch { error ->
                    _uiState.value = _uiState.value.copy(
                        error = error.message,
                        isLoading = false
                    )
                }
                .collect { events ->
                    _uiState.value = _uiState.value.copy(
                        events = events,
                        isLoading = false,
                        error = null
                    )
                    applyFilters()
                }
        }
    }
    
    private fun observeFilters() {
        viewModelScope.launch {
            combine(
                _searchQuery,
                _selectedCategory,
                _selectedCity
            ) { query, category, city ->
                Triple(query, category, city)
            }.collect { (query, category, city) ->
                _uiState.value = _uiState.value.copy(
                    searchQuery = query,
                    selectedCategory = category,
                    selectedCity = city
                )
                applyFilters()
            }
        }
    }
    
    private fun applyFilters() {
        val currentState = _uiState.value
        var filteredEvents = currentState.events
        
        // Apply search query filter
        if (currentState.searchQuery.isNotBlank()) {
            filteredEvents = filteredEvents.filter { event ->
                event.title.contains(currentState.searchQuery, ignoreCase = true) ||
                event.description.contains(currentState.searchQuery, ignoreCase = true) ||
                event.venue.contains(currentState.searchQuery, ignoreCase = true) ||
                event.organizerName.contains(currentState.searchQuery, ignoreCase = true)
            }
        }
        
        // Apply category filter
        currentState.selectedCategory?.let { category ->
            filteredEvents = filteredEvents.filter { event ->
                event.category == category
            }
        }
        
        // Apply city filter
        if (currentState.selectedCity.isNotBlank()) {
            filteredEvents = filteredEvents.filter { event ->
                event.city.contains(currentState.selectedCity, ignoreCase = true)
            }
        }
        
        _uiState.value = currentState.copy(
            filteredEvents = filteredEvents
        )
    }
    
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
    
    fun onCategorySelected(category: EventCategory?) {
        _selectedCategory.value = category
    }
    
    fun onCitySelected(city: String) {
        _selectedCity.value = city
    }
    
    fun clearFilters() {
        _searchQuery.value = ""
        _selectedCategory.value = null
        _selectedCity.value = ""
    }
    
    fun refreshEvents() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        // In a real app, this would trigger a network refresh
        // For now, the data will automatically update through the flow
    }
    
    fun getAvailableCities(): List<String> {
        return _uiState.value.events
            .map { it.city }
            .distinct()
            .sorted()
    }
}