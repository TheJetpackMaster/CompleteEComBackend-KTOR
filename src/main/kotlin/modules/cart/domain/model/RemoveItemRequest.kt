package modules.cart.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RemoveItemRequest(val productId: String)