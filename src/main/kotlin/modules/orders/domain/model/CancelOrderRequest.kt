package modules.orders.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CancelOrderRequest(val orderId: String)