package modules.orders.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: String,
    val userId: String,
    val items: List<OrderItem>,
    val totalPrice: Double,
    val status: String, // PENDING, COMPLETED, CANCELLED
    val createdAt: Long
)