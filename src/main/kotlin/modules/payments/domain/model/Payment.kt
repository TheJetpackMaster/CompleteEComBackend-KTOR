package modules.payments.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Payment(
    val id: String,
    val orderId: String,
    val userId: String,
    val amount: Double,
    val status: PaymentStatus,
    val createdAt: Long
)