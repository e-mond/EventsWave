package com.eventwave.app.di

import com.eventwave.app.data.local.dao.EventDao
import com.eventwave.app.data.local.dao.TicketDao
import com.eventwave.app.data.local.dao.UserDao
import com.eventwave.app.data.repository.EventRepository
import com.eventwave.app.data.repository.TicketRepository
import com.eventwave.app.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    
    @Provides
    @Singleton
    fun provideEventRepository(eventDao: EventDao): EventRepository {
        return EventRepository(eventDao)
    }
    
    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepository(userDao)
    }
    
    @Provides
    @Singleton
    fun provideTicketRepository(
        ticketDao: TicketDao,
        eventRepository: EventRepository
    ): TicketRepository {
        return TicketRepository(ticketDao, eventRepository)
    }
}