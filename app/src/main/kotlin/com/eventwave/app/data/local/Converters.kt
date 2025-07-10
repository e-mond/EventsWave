package com.eventwave.app.data.local

import androidx.room.TypeConverter
import com.eventwave.app.data.model.EventCategory
import com.eventwave.app.data.model.PaymentMethod
import com.eventwave.app.data.model.TicketStatus
import com.eventwave.app.data.model.UserType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {
    
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    
    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? {
        return dateTime?.format(formatter)
    }
    
    @TypeConverter
    fun toLocalDateTime(dateString: String?): LocalDateTime? {
        return try {
            dateString?.let { LocalDateTime.parse(it, formatter) }
        } catch (e: Exception) {
            null
        }
    }
    
    @TypeConverter
    fun fromEventCategory(category: EventCategory): String {
        return category.name
    }
    
    @TypeConverter
    fun toEventCategory(categoryString: String): EventCategory {
        return try {
            EventCategory.valueOf(categoryString)
        } catch (e: IllegalArgumentException) {
            EventCategory.OTHER // Default fallback
        }
    }
    
    @TypeConverter
    fun fromUserType(userType: UserType): String {
        return userType.name
    }
    
    @TypeConverter
    fun toUserType(userTypeString: String): UserType {
        return try {
            UserType.valueOf(userTypeString)
        } catch (e: IllegalArgumentException) {
            UserType.ATTENDEE // Default fallback
        }
    }
    
    @TypeConverter
    fun fromTicketStatus(status: TicketStatus): String {
        return status.name
    }
    
    @TypeConverter
    fun toTicketStatus(statusString: String): TicketStatus {
        return try {
            TicketStatus.valueOf(statusString)
        } catch (e: IllegalArgumentException) {
            TicketStatus.ACTIVE // Default fallback
        }
    }
    
    @TypeConverter
    fun fromPaymentMethod(method: PaymentMethod): String {
        return method.name
    }
    
    @TypeConverter
    fun toPaymentMethod(methodString: String): PaymentMethod {
        return try {
            PaymentMethod.valueOf(methodString)
        } catch (e: IllegalArgumentException) {
            PaymentMethod.CARD // Default fallback
        }
    }
}