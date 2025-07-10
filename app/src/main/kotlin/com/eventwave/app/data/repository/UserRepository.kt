package com.eventwave.app.data.repository

import com.eventwave.app.data.local.dao.UserDao
import com.eventwave.app.data.model.User
import com.eventwave.app.data.model.UserType
import com.eventwave.app.data.model.sampleUsers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    
    suspend fun getUserById(userId: String): User? = userDao.getUserById(userId)
    
    suspend fun getUserByEmail(email: String): User? = userDao.getUserByEmail(email)
    
    fun getUsersByType(userType: UserType): Flow<List<User>> = 
        userDao.getUsersByType(userType)
    
    suspend fun createUser(user: User) {
        userDao.insertUser(user)
    }
    
    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }
    
    suspend fun deleteUser(userId: String) {
        userDao.deleteUserById(userId)
    }
    
    suspend fun initializeSampleData() {
        // Check if we already have users
        val existingUser = userDao.getUserById("user1")
        if (existingUser == null) {
            userDao.insertUsers(sampleUsers)
        }
    }
    
    // Simulated login - in a real app this would connect to authentication service
    suspend fun login(email: String, password: String): User? {
        return userDao.getUserByEmail(email)
    }
    
    // Get current logged in user - for demo purposes, return the sample user
    suspend fun getCurrentUser(): User? {
        return userDao.getUserById("user1")
    }
}