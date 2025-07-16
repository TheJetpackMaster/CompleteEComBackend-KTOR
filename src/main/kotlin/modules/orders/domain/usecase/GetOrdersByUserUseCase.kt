package modules.orders.domain.usecase

import modules.orders.domain.model.Order
import modules.orders.domain.repository.IOrderRepository

class GetOrdersByUserUseCase(private val repository: IOrderRepository) {
    suspend operator fun invoke(userId: String): List<Order> = repository.getOrdersByUser(userId)
}