package com.ecom.modules.products.domain.model

import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.util.UUID


@Serializable
data class Product(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val price: Int,
    val stock: Int,
    val ownerId: String // seller ID
)