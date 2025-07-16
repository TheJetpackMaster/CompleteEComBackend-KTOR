package modules.products.domain.usecase

import com.ecom.modules.products.domain.model.Product
import com.ecom.modules.products.domain.repository.IProductRepository

class GetAllProductsUseCase(
    private val repository: IProductRepository
) {
    suspend operator fun invoke(): List<Product> = repository.getAllProducts()
}