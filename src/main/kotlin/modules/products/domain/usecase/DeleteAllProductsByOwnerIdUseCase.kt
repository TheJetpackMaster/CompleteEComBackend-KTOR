package com.ecom.modules.products.domain.usecase

import com.ecom.modules.products.domain.repository.IProductRepository

class DeleteAllProductsByOwnerIdUseCase(
    private val productRepository: IProductRepository
) {
    suspend operator fun invoke(ownerId:String): Boolean{
        return productRepository.deleteAllProducts(ownerId)
    }
}