package modules.cart.domain.usecase

import modules.cart.domain.repository.ICartRepository

class RemoveFromCartUseCase(private val repository: ICartRepository) {
    suspend operator fun invoke(userId: String, productId: String): Boolean {
        return repository.removeFromCart(userId, productId)
    }
}
