package com.ecom.modules.users.data

import core.model.Role
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table


object UsersTable : Table("users") {
    val id = varchar("id", 36)
    val name = varchar("name", 100)
    val email = varchar("email", 100).uniqueIndex()
    val password = varchar("password", 255)
    val role = enumerationByName("role", 20, Role::class).default(Role.USER)
    val isVerifiedSeller = bool("is_verified_seller").default(false)

    override val primaryKey = PrimaryKey(id)
}
