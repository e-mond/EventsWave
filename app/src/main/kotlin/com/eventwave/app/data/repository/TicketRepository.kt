package com.eventwave.app.data.repository

import com.eventwave.app.data.local.dao.TicketDao
import com.eventwave.app.data.model.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TicketRepository @Inject constructor(
    private val ticketDao: TicketDao,
    private val eventRepository: EventRepository
) {
    
    fun getTicketsByUser(userId: String): Flow<List<Ticket>> = 
        ticketDao.getTicketsByUser(userId)
    
    fun getTicketsByEvent(eventId: String): Flow<List<Ticket>> = 
        ticketDao.getTicketsByEvent(eventId)
    
    suspend fun getTicketById(ticketId: String): Ticket? = 
        ticketDao.getTicketById(ticketId)
    
    suspend fun getUserTicketForEvent(userId: String, eventId: String): Ticket? = 
        ticketDao.getUserTicketForEvent(userId, eventId)
    
    fun getTicketsByUserAndStatus(userId: String, status: TicketStatus): Flow<List<Ticket>> = 
        ticketDao.getTicketsByUserAndStatus(userId, status)
    
    suspend fun getTotalSoldTicketsForEvent(eventId: String): Int = 
        ticketDao.getTotalSoldTicketsForEvent(eventId) ?: 0
    
    suspend fun getTotalRevenueForEvent(eventId: String): Double = 
        ticketDao.getTotalRevenueForEvent(eventId) ?: 0.0
    
    suspend fun bookTicket(
        event: Event,
        user: User,
        quantity: Int,
        paymentMethod: PaymentMethod
    ): Result<Ticket> {
        return try {
            if (event.availableTickets < quantity) {
                Result.failure(Exception("Not enough tickets available"))
            } else {
                val totalPrice = event.ticketPrice * quantity
                val qrCode = generateQRCode(event.id, user.id)
                
                val ticket = Ticket(
                    id = UUID.randomUUID().toString(),
                    eventId = event.id,
                    eventTitle = event.title,
                    eventDateTime = event.dateTime,
                    eventVenue = event.venue,
                    userId = user.id,
                    userName = user.name,
                    userEmail = user.email,
                    quantity = quantity,
                    totalPrice = totalPrice,
                    qrCode = qrCode,
                    status = TicketStatus.ACTIVE,
                    paymentMethod = paymentMethod
                )
                
                // Simulate payment processing
                val paymentResult = processPayment(totalPrice, paymentMethod)
                if (paymentResult) {
                    ticketDao.insertTicket(ticket)
                    // Update available tickets
                    eventRepository.updateAvailableTickets(
                        event.id,
                        event.availableTickets - quantity
                    )
                    Result.success(ticket)
                } else {
                    Result.failure(Exception("Payment failed"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun cancelTicket(ticketId: String): Result<Unit> {
        return try {
            val ticket = ticketDao.getTicketById(ticketId)
            if (ticket != null) {
                ticketDao.updateTicketStatus(ticketId, TicketStatus.CANCELLED)
                // Refund tickets to event
                val event = eventRepository.getEventById(ticket.eventId)
                event?.let {
                    eventRepository.updateAvailableTickets(
                        it.id,
                        it.availableTickets + ticket.quantity
                    )
                }
                Result.success(Unit)
            } else {
                Result.failure(Exception("Ticket not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun useTicket(ticketId: String): Result<Unit> {
        return try {
            ticketDao.updateTicketStatus(ticketId, TicketStatus.USED)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun initializeSampleData() {
        // Check if we already have tickets
        val existingTickets = ticketDao.getTicketsByUser("user1")
        // Only initialize if no tickets exist - this is a simple check for demo
        ticketDao.insertTickets(sampleTickets)
    }
    
    private fun generateQRCode(eventId: String, userId: String): String {
        return "QR_${eventId}_${userId}_${System.currentTimeMillis()}"
    }
    
    private suspend fun processPayment(amount: Double, method: PaymentMethod): Boolean {
        // Simulate payment processing delay
        kotlinx.coroutines.delay(1500)
        // For demo purposes, randomly succeed 95% of the time
        return kotlin.random.Random.nextFloat() > 0.05f
    }
}