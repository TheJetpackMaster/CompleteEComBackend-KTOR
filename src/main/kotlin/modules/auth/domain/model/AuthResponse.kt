package modules.auth.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String,
    val userId: String
)