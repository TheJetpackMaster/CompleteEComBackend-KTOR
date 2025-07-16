package modules.cart.domain.repository

import modules.cart.domain.model.Cart

interface ICartRepository {
    suspend fun getCart(userId: String): Cart?
    suspend fun addToCart(userId: String, productId: String, quantity: Int): Boolean
    suspend fun removeFromCart(userId: String, productId: String): Boolean
    suspend fun clearCart(userId: String): Boolean
}