package com.eventwave.app.di

import android.content.Context
import androidx.room.Room
import com.eventwave.app.data.local.EventWaveDatabase
import com.eventwave.app.data.local.dao.EventDao
import com.eventwave.app.data.local.dao.TicketDao
import com.eventwave.app.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): EventWaveDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            EventWaveDatabase::class.java,
            "eventwave_database"
        )
        .fallbackToDestructiveMigration()
        .build()
    }
    
    @Provides
    fun provideEventDao(database: EventWaveDatabase): EventDao {
        return database.eventDao()
    }
    
    @Provides
    fun provideUserDao(database: EventWaveDatabase): UserDao {
        return database.userDao()
    }
    
    @Provides
    fun provideTicketDao(database: EventWaveDatabase): TicketDao {
        return database.ticketDao()
    }
}