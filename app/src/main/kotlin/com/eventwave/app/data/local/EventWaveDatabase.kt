package com.eventwave.app.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.eventwave.app.data.local.dao.EventDao
import com.eventwave.app.data.local.dao.TicketDao
import com.eventwave.app.data.local.dao.UserDao
import com.eventwave.app.data.model.Event
import com.eventwave.app.data.model.Ticket
import com.eventwave.app.data.model.User

@Database(
    entities = [Event::class, User::class, Ticket::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class EventWaveDatabase : RoomDatabase() {
    
    abstract fun eventDao(): EventDao
    abstract fun userDao(): UserDao
    abstract fun ticketDao(): TicketDao
    
    companion object {
        @Volatile
        private var INSTANCE: EventWaveDatabase? = null
        
        fun getDatabase(context: Context): EventWaveDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventWaveDatabase::class.java,
                    "eventwave_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}