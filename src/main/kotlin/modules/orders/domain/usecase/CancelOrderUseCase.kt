package modules.orders.domain.usecase

import modules.orders.domain.repository.IOrderRepository

class CancelOrderUseCase(private val repository: IOrderRepository) {
    suspend operator fun invoke(orderId: String, userId: String): Boolean = repository.cancelOrder(orderId, userId)
}