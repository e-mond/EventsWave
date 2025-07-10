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
        return dateString?.let { LocalDateTime.parse(it, formatter) }
    }
    
    @TypeConverter
    fun fromEventCategory(category: EventCategory): String {
        return category.name
    }
    
    @TypeConverter
    fun toEventCategory(categoryString: String): EventCategory {
        return EventCategory.valueOf(categoryString)
    }
    
    @TypeConverter
    fun fromUserType(userType: UserType): String {
        return userType.name
    }
    
    @TypeConverter
    fun toUserType(userTypeString: String): UserType {
        return UserType.valueOf(userTypeString)
    }
    
    @TypeConverter
    fun fromTicketStatus(status: TicketStatus): String {
        return status.name
    }
    
    @TypeConverter
    fun toTicketStatus(statusString: String): TicketStatus {
        return TicketStatus.valueOf(statusString)
    }
    
    @TypeConverter
    fun fromPaymentMethod(method: PaymentMethod): String {
        return method.name
    }
    
    @TypeConverter
    fun toPaymentMethod(methodString: String): PaymentMethod {
        return PaymentMethod.valueOf(methodString)
    }
}