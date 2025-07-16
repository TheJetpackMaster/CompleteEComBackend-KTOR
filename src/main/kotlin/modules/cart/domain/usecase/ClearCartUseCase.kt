package modules.cart.domain.usecase

import modules.cart.domain.repository.ICartRepository

class ClearCartUseCase(private val repository: ICartRepository) {
    suspend operator fun invoke(userId: String): Boolean {
        return repository.clearCart(userId)
    }
}