package modules.users.domain.model

import core.model.Role
import java.time.LocalDateTime
import java.util.UUID

data class User(
    val id: UUID,
    val name: String,
    val email: String,
    val password: String,
    val role: Role,
    val isVerifiedSeller: Boolean = false,
)