package com.ecom.modules.products.domain.usecase

import com.ecom.modules.products.domain.model.Product
import com.ecom.modules.products.domain.repository.IProductRepository
import com.ecom.modules.users.domain.repository.IUserRepository

class UpdateProductByIdUseCase(
    private val productRepository: IProductRepository
) {
    suspend operator fun invoke(productId: String,updatedProduct: Product): Product?{
        return productRepository.updateProductById(productId,updatedProduct)
    }
}