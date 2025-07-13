package modules.products.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val id: String,
    val name: String,
    val description: String,
    val price: Int,
    val stock: Int,
    val sellerId: String // or change to sellerName if needed
)