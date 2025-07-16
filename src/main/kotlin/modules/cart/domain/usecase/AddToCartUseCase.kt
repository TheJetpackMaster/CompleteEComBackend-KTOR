package modules.cart.domain.usecase

import modules.cart.domain.repository.ICartRepository

class AddToCartUseCase(private val repository: ICartRepository) {
    suspend operator fun invoke(userId: String, productId: String, quantity: Int): Boolean {
        return repository.addToCart(userId, productId, quantity)
    }
}
