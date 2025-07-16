package modules.cart.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Cart(
    val userId: String,
    val items: List<CartItem>
)