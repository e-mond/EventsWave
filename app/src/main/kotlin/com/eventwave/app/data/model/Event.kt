package com.eventwave.app.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
@Entity(tableName = "events")
data class Event(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val venue: String,
    val address: String,
    val city: String,
    val dateTime: LocalDateTime,
    val category: EventCategory,
    val ticketPrice: Double,
    val totalTickets: Int,
    val availableTickets: Int,
    val organizerId: String,
    val organizerName: String,
    val isActive: Boolean = true,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) : Parcelable

enum class EventCategory(val displayName: String) {
    CONCERT("Concert"),
    FESTIVAL("Festival"),
    COMEDY("Comedy Show"),
    THEATER("Theater"),
    SPORTS("Sports"),
    WORKSHOP("Workshop"),
    CONFERENCE("Conference"),
    PARTY("Party"),
    OTHER("Other")
}

val sampleEvents = listOf(
    Event(
        id = "1",
        title = "Summer Music Festival",
        description = "The biggest music festival of the year featuring top artists from around the world. Experience three days of non-stop music, food, and fun.",
        imageUrl = "https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?w=800",
        venue = "Central Park",
        address = "Central Park, Manhattan",
        city = "New York",
        dateTime = LocalDateTime.now().plusDays(30),
        category = EventCategory.FESTIVAL,
        ticketPrice = 150.0,
        totalTickets = 5000,
        availableTickets = 2500,
        organizerId = "org1",
        organizerName = "Festival Productions"
    ),
    Event(
        id = "2",
        title = "Jazz Night Live",
        description = "An intimate evening of smooth jazz featuring local and international artists in a cozy venue.",
        imageUrl = "https://images.unsplash.com/photo-1415201364774-f6f0bb35f28f?w=800",
        venue = "Blue Note",
        address = "131 W 3rd St",
        city = "New York",
        dateTime = LocalDateTime.now().plusDays(7),
        category = EventCategory.CONCERT,
        ticketPrice = 75.0,
        totalTickets = 200,
        availableTickets = 45,
        organizerId = "org2",
        organizerName = "Blue Note Productions"
    ),
    Event(
        id = "3",
        title = "Comedy Central Live",
        description = "Laugh until you cry with the best comedians in the city. Special guest appearances guaranteed!",
        imageUrl = "https://images.unsplash.com/photo-1527224857830-43a7acc85260?w=800",
        venue = "Comedy Cellar",
        address = "117 MacDougal St",
        city = "New York",
        dateTime = LocalDateTime.now().plusDays(3),
        category = EventCategory.COMEDY,
        ticketPrice = 35.0,
        totalTickets = 150,
        availableTickets = 78,
        organizerId = "org3",
        organizerName = "Laugh Track Entertainment"
    ),
    Event(
        id = "4",
        title = "Tech Conference 2024",
        description = "The latest in technology and innovation. Network with industry leaders and learn about cutting-edge developments.",
        imageUrl = "https://images.unsplash.com/photo-1540575467063-178a50c2df87?w=800",
        venue = "Convention Center",
        address = "655 W 34th St",
        city = "New York",
        dateTime = LocalDateTime.now().plusDays(45),
        category = EventCategory.CONFERENCE,
        ticketPrice = 200.0,
        totalTickets = 1000,
        availableTickets = 750,
        organizerId = "org4",
        organizerName = "TechEvents Inc"
    ),
    Event(
        id = "5",
        title = "Rooftop Party",
        description = "Dance under the stars at the hottest rooftop party in the city. DJ sets, cocktails, and amazing city views.",
        imageUrl = "https://images.unsplash.com/photo-1470229722913-7c0e2dbbafd3?w=800",
        venue = "Sky Lounge",
        address = "230 5th Ave",
        city = "New York",
        dateTime = LocalDateTime.now().plusDays(14),
        category = EventCategory.PARTY,
        ticketPrice = 50.0,
        totalTickets = 300,
        availableTickets = 120,
        organizerId = "org5",
        organizerName = "Sky Events"
    )
)