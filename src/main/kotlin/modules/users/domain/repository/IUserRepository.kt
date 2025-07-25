package com.ecom.modules.users.domain.repository

import modules.users.domain.model.User
import java.util.UUID


interface IUserRepository {

    // Save new user
    suspend fun save(user: User): User

    // Update user by ID (pass updated user)
    suspend fun updateUserById(id: String, updatedUser: User): User?

    // Delete user by ID
    suspend fun deleteUserById(id: String): Boolean

    // Find user by email
    suspend fun findByEmail(email: String): User?

    // Find user by ID
    suspend fun findById(id: String): User?
}