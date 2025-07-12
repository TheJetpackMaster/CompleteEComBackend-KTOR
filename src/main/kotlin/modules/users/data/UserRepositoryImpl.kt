package com.ecom.modules.users.data

import com.ecom.config.DatabaseFactory.dbQuery
import com.ecom.modules.users.domain.repository.IUserRepository
import modules.users.domain.model.User
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.update
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import java.util.UUID

class UserRepositoryImpl : IUserRepository {

    // Save New user
    override suspend fun save(user: User): User = dbQuery {
        UsersTable.insert {
            it[id] = user.id
            it[name] = user.name
            it[email] = user.email
            it[password] = user.password
            it[role] = user.role
            it[isVerifiedSeller] = user.isVerifiedSeller
        }

        return@dbQuery user
    }

    // Update user by id
    override suspend fun updateUserById(
        id: String,
        updatedUser: User
    ): User? = dbQuery{
        UsersTable.update({ UsersTable.id eq id}){
            it[name] = updatedUser.name
            it[email] = updatedUser.email
            it[password] = updatedUser.password
            it[role] = updatedUser.role
            it[isVerifiedSeller] = updatedUser.isVerifiedSeller
        }

        //Return updated user
        return@dbQuery findById(id)
    }

    // Delete user by id
    override suspend fun deleteUserById(id: String): Boolean = dbQuery{
        UsersTable.deleteWhere{ UsersTable.id eq (id)}
        return@dbQuery true
    }


    // Find User By email
    override suspend fun findByEmail(email: String): User? = dbQuery {
        UsersTable
            .selectAll() // <-- This ensures 'id' is included in ResultRow
            .where { UsersTable.email eq email }
            .map { it.toUser() }
            .singleOrNull()
    }


    // Find User By id
    override suspend fun findById(id: String): User? = dbQuery {
        UsersTable
            .selectAll()
            .where { UsersTable.id eq id }
            .map { it.toUser() }
            .singleOrNull()
    }

}