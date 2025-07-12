package com.ecom.modules.users.data

import modules.users.domain.model.User
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toUser() = User(
    id = this[UsersTable.id],
    name = this[UsersTable.name],
    email = this[UsersTable.email],
    password = this[UsersTable.password],
    role = this[UsersTable.role],
    isVerifiedSeller = this[UsersTable.isVerifiedSeller]
)
