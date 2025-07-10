package com.eventwave.app.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
@Entity(tableName = "tickets")
data class Ticket(
    @PrimaryKey
    val id: String,
    val eventId: String,
    val eventTitle: String,
    val eventDateTime: LocalDateTime,
    val eventVenue: String,
    val userId: String,
    val userName: String,
    val userEmail: String,
    val quantity: Int,
    val totalPrice: Double,
    val qrCode: String,
    val status: TicketStatus,
    val purchasedAt: LocalDateTime,
    val paymentMethod: PaymentMethod = PaymentMethod.CARD
) : Parcelable

enum class TicketStatus {
    ACTIVE,
    USED,
    CANCELLED,
    REFUNDED
}

enum class PaymentMethod(val displayName: String) {
    CARD("Credit/Debit Card"),
    MOBILE_MONEY("Mobile Money"),
    PAYPAL("PayPal"),
    BANK_TRANSFER("Bank Transfer")
}

// Sample tickets for testing
val sampleTickets = listOf(
    Ticket(
        id = "ticket1",
        eventId = "2",
        eventTitle = "Jazz Night Live",
        eventDateTime = LocalDateTime.now().plusDays(7),
        eventVenue = "Blue Note",
        userId = "user1",
        userName = "John Doe",
        userEmail = "john.doe@email.com",
        quantity = 2,
        totalPrice = 150.0,
        qrCode = "QR_JAZZ_NIGHT_${System.currentTimeMillis()}",
        status = TicketStatus.ACTIVE,
        purchasedAt = LocalDateTime.now().minusDays(5),
        paymentMethod = PaymentMethod.CARD
    ),
    Ticket(
        id = "ticket2",
        eventId = "3",
        eventTitle = "Comedy Central Live",
        eventDateTime = LocalDateTime.now().plusDays(3),
        eventVenue = "Comedy Cellar",
        userId = "user1",
        userName = "John Doe",
        userEmail = "john.doe@email.com",
        quantity = 1,
        totalPrice = 35.0,
        qrCode = "QR_COMEDY_${System.currentTimeMillis()}",
        status = TicketStatus.ACTIVE,
        purchasedAt = LocalDateTime.now().minusDays(2),
        paymentMethod = PaymentMethod.MOBILE_MONEY
    )
)