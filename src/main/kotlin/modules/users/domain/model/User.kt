package modules.users.domain.model

import core.model.Role
import kotlinx.serialization.Serializable
import java.util.UUID


@Serializable
data class User(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val email: String,
    val password: String,
    val role: Role,
    val isVerifiedSeller: Boolean = false,
)