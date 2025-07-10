package com.eventwave.app.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val profileImageUrl: String? = null,
    val userType: UserType,
    val city: String = "",
    val phone: String = "",
    val createdAt: LocalDateTime
) : Parcelable

enum class UserType {
    ATTENDEE,
    ORGANIZER
}

// Sample users for testing
val sampleUsers = listOf(
    User(
        id = "user1",
        name = "John Doe",
        email = "john.doe@email.com",
        userType = UserType.ATTENDEE,
        city = "New York",
        createdAt = LocalDateTime.now().minusDays(30)
    ),
    User(
        id = "org1",
        name = "Festival Productions",
        email = "contact@festivalproductions.com",
        userType = UserType.ORGANIZER,
        city = "New York",
        createdAt = LocalDateTime.now().minusDays(60)
    ),
    User(
        id = "org2",
        name = "Blue Note Productions",
        email = "info@bluenote.com",
        userType = UserType.ORGANIZER,
        city = "New York",
        createdAt = LocalDateTime.now().minusDays(45)
    ),
    User(
        id = "org3",
        name = "Laugh Track Entertainment",
        email = "bookings@laughtrack.com",
        userType = UserType.ORGANIZER,
        city = "New York",
        createdAt = LocalDateTime.now().minusDays(55)
    ),
    User(
        id = "org4",
        name = "TechEvents Inc",
        email = "contact@techevents.com",
        userType = UserType.ORGANIZER,
        city = "New York",
        createdAt = LocalDateTime.now().minusDays(40)
    ),
    User(
        id = "org5",
        name = "Sky Events",
        email = "hello@skyevents.com",
        userType = UserType.ORGANIZER,
        city = "New York",
        createdAt = LocalDateTime.now().minusDays(35)
    )
)