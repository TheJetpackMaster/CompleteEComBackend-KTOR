package modules.orders.domain.usecase

import modules.orders.domain.repository.IOrderRepository

class PlaceOrderUseCase(private val repository: IOrderRepository) {
    suspend operator fun invoke(userId: String): Boolean = repository.placeOrder(userId)
}