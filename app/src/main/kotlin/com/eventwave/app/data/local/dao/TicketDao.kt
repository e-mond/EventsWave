package com.eventwave.app.data.local.dao

import androidx.room.*
import com.eventwave.app.data.model.Ticket
import com.eventwave.app.data.model.TicketStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketDao {
    
    @Query("SELECT * FROM tickets WHERE userId = :userId ORDER BY purchasedAt DESC")
    fun getTicketsByUser(userId: String): Flow<List<Ticket>>
    
    @Query("SELECT * FROM tickets WHERE eventId = :eventId ORDER BY purchasedAt DESC")
    fun getTicketsByEvent(eventId: String): Flow<List<Ticket>>
    
    @Query("SELECT * FROM tickets WHERE id = :ticketId")
    suspend fun getTicketById(ticketId: String): Ticket?
    
    @Query("SELECT * FROM tickets WHERE userId = :userId AND eventId = :eventId")
    suspend fun getUserTicketForEvent(userId: String, eventId: String): Ticket?
    
    @Query("SELECT * FROM tickets WHERE userId = :userId AND status = :status ORDER BY purchasedAt DESC")
    fun getTicketsByUserAndStatus(userId: String, status: TicketStatus): Flow<List<Ticket>>
    
    @Query("SELECT SUM(quantity) FROM tickets WHERE eventId = :eventId AND status IN ('ACTIVE', 'USED')")
    suspend fun getTotalSoldTicketsForEvent(eventId: String): Int?
    
    @Query("SELECT SUM(totalPrice) FROM tickets WHERE eventId = :eventId AND status IN ('ACTIVE', 'USED')")
    suspend fun getTotalRevenueForEvent(eventId: String): Double?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: Ticket)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTickets(tickets: List<Ticket>)
    
    @Update
    suspend fun updateTicket(ticket: Ticket)
    
    @Query("UPDATE tickets SET status = :status WHERE id = :ticketId")
    suspend fun updateTicketStatus(ticketId: String, status: TicketStatus)
    
    @Delete
    suspend fun deleteTicket(ticket: Ticket)
    
    @Query("DELETE FROM tickets WHERE id = :ticketId")
    suspend fun deleteTicketById(ticketId: String)
}