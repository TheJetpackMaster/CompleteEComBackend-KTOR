package modules.orders.domain.repository

import modules.orders.domain.model.Order

interface IOrderRepository {
    suspend fun placeOrder(userId: String): Boolean
    suspend fun getOrdersByUser(userId: String): List<Order>
    suspend fun cancelOrder(orderId: String, userId: String): Boolean
    suspend fun getOrderById(orderId: String, userId: String): Order?
}