package modules.orders.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class OrderItem(
    val productId: String,
    val quantity: Int
)