package com.ecom.modules.products.domain.usecase

import com.ecom.modules.products.domain.model.Product
import com.ecom.modules.products.domain.repository.IProductRepository

class FindProductByIdUseCase(
    private val productRepository: IProductRepository
) {
    suspend operator fun invoke(productId: String): Product?{
       return productRepository.findProductById(productId)

    }
}