package modules.cart.domain.usecase

import modules.cart.domain.model.Cart
import modules.cart.domain.repository.ICartRepository

class GetCartUseCase(private val repository: ICartRepository) {
    suspend operator fun invoke(userId: String): Cart? {
        return repository.getCart(userId)
    }
}