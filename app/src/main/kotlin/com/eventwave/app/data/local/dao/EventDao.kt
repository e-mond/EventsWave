package com.eventwave.app.data.local.dao

import androidx.room.*
import com.eventwave.app.data.model.Event
import com.eventwave.app.data.model.EventCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    
    @Query("SELECT * FROM events WHERE isActive = 1 ORDER BY dateTime ASC")
    fun getAllActiveEvents(): Flow<List<Event>>
    
    @Query("SELECT * FROM events WHERE id = :eventId")
    suspend fun getEventById(eventId: String): Event?
    
    @Query("SELECT * FROM events WHERE organizerId = :organizerId ORDER BY dateTime ASC")
    fun getEventsByOrganizer(organizerId: String): Flow<List<Event>>
    
    @Query("SELECT * FROM events WHERE city LIKE '%' || :city || '%' AND isActive = 1 ORDER BY dateTime ASC")
    fun getEventsByCity(city: String): Flow<List<Event>>
    
    @Query("SELECT * FROM events WHERE category = :category AND isActive = 1 ORDER BY dateTime ASC")
    fun getEventsByCategory(category: EventCategory): Flow<List<Event>>
    
    @Query("SELECT * FROM events WHERE (title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' OR venue LIKE '%' || :query || '%') AND isActive = 1 ORDER BY dateTime ASC")
    fun searchEvents(query: String): Flow<List<Event>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Event)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<Event>)
    
    @Update
    suspend fun updateEvent(event: Event)
    
    @Query("UPDATE events SET availableTickets = :availableTickets WHERE id = :eventId")
    suspend fun updateAvailableTickets(eventId: String, availableTickets: Int)
    
    @Delete
    suspend fun deleteEvent(event: Event)
    
    @Query("DELETE FROM events WHERE id = :eventId")
    suspend fun deleteEventById(eventId: String)
    
    @Query("UPDATE events SET isActive = 0 WHERE id = :eventId")
    suspend fun deactivateEvent(eventId: String)
}