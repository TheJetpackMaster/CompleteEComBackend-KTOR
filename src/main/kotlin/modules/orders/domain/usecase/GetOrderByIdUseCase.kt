package modules.orders.domain.usecase

import modules.orders.domain.model.Order
import modules.orders.domain.repository.IOrderRepository

class GetOrderByIdUseCase(private val repository: IOrderRepository) {
    suspend operator fun invoke(orderId: String, userId: String): Order? =
        repository.getOrderById(orderId, userId)
}