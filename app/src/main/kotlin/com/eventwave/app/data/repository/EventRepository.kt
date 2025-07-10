package com.eventwave.app.data.repository

import com.eventwave.app.data.local.dao.EventDao
import com.eventwave.app.data.model.Event
import com.eventwave.app.data.model.EventCategory
import com.eventwave.app.data.model.sampleEvents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(
    private val eventDao: EventDao
) {
    
    fun getAllActiveEvents(): Flow<List<Event>> = eventDao.getAllActiveEvents()
    
    suspend fun getEventById(eventId: String): Event? = eventDao.getEventById(eventId)
    
    fun getEventsByOrganizer(organizerId: String): Flow<List<Event>> = 
        eventDao.getEventsByOrganizer(organizerId)
    
    fun getEventsByCity(city: String): Flow<List<Event>> = 
        eventDao.getEventsByCity(city)
    
    fun getEventsByCategory(category: EventCategory): Flow<List<Event>> = 
        eventDao.getEventsByCategory(category)
    
    fun searchEvents(query: String): Flow<List<Event>> = 
        eventDao.searchEvents(query)
    
    suspend fun createEvent(event: Event) {
        eventDao.insertEvent(event)
    }
    
    suspend fun updateEvent(event: Event) {
        eventDao.updateEvent(event)
    }
    
    suspend fun updateAvailableTickets(eventId: String, availableTickets: Int) {
        eventDao.updateAvailableTickets(eventId, availableTickets)
    }
    
    suspend fun deleteEvent(eventId: String) {
        eventDao.deleteEventById(eventId)
    }
    
    suspend fun deactivateEvent(eventId: String) {
        eventDao.deactivateEvent(eventId)
    }
    
    suspend fun initializeSampleData() {
        val currentEvents = eventDao.getAllActiveEvents().first()
        if (currentEvents.isEmpty()) {
            eventDao.insertEvents(sampleEvents)
        }
    }
}