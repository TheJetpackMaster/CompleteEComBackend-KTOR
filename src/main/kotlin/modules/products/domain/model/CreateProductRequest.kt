package modules.products.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateProductRequest(
    val name: String,
    val description: String,
    val price: Int,
    val stock: Int
)