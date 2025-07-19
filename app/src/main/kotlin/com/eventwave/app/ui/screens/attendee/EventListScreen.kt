@file:OptIn(ExperimentalMaterial3Api::class)
package com.eventwave.app.ui.screens.attendee

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.eventwave.app.data.model.EventCategory
import com.eventwave.app.navigation.NavigationRoutes
import com.eventwave.app.ui.components.EventCard
import com.eventwave.app.ui.viewmodel.EventListViewModel

@Composable
fun EventListScreen(
    navController: NavController,
    viewModel: EventListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showFilters by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Discover Events",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        
        Text(
            text = "Your vibe. Your city. Your wave.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Search Bar
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = viewModel::onSearchQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search events, venues, artists...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            trailingIcon = {
                Row {
                    if (uiState.searchQuery.isNotBlank()) {
                        IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear search"
                            )
                        }
                    }
                    IconButton(onClick = { showFilters = !showFilters }) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filters",
                            tint = if (uiState.selectedCategory != null || uiState.selectedCity.isNotBlank()) 
                                MaterialTheme.colorScheme.primary 
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Filters Section
        if (showFilters) {
            FilterSection(
                selectedCategory = uiState.selectedCategory,
                selectedCity = uiState.selectedCity,
                availableCities = viewModel.getAvailableCities(),
                onCategorySelected = viewModel::onCategorySelected,
                onCitySelected = viewModel::onCitySelected,
                onClearFilters = {
                    viewModel.clearFilters()
                    showFilters = false
                }
            )
        }
        
        // Events Grid
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Text(
                text = "Error: ${uiState.error}",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            val eventsToShow = if (uiState.searchQuery.isNotBlank() || 
                                   uiState.selectedCategory != null || 
                                   uiState.selectedCity.isNotBlank()) {
                uiState.filteredEvents
            } else {
                uiState.events
            }
            
            if (eventsToShow.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No events found",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    verticalItemSpacing = 16.dp,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(eventsToShow) { event ->
                        EventCard(
                            event = event,
                            onClick = {
                                navController.navigate(
                                    NavigationRoutes.EventDetail.createRoute(event.id)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterSection(
    selectedCategory: EventCategory?,
    selectedCity: String,
    availableCities: List<String>,
    onCategorySelected: (EventCategory?) -> Unit,
    onCitySelected: (String) -> Unit,
    onClearFilters: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filters",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                TextButton(onClick = onClearFilters) {
                    Text("Clear All")
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Categories
            Text(
                text = "Categories",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(EventCategory.values()) { category ->
                    FilterChip(
                        onClick = {
                            onCategorySelected(
                                if (selectedCategory == category) null else category
                            )
                        },
                        label = { Text(category.displayName) },
                        selected = selectedCategory == category
                    )
                }
            }
            
            if (availableCities.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                
                // Cities
                Text(
                    text = "Cities",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(availableCities) { city ->
                        FilterChip(
                            onClick = {
                                onCitySelected(
                                    if (selectedCity == city) "" else city
                                )
                            },
                            label = { Text(city) },
                            selected = selectedCity == city
                        )
                    }
                }
            }
        }
    }
}