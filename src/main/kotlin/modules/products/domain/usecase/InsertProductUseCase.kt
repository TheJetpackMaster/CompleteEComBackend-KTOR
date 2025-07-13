package com.ecom.modules.products.domain.usecase

import com.ecom.modules.products.domain.model.Product
import com.ecom.modules.products.domain.repository.IProductRepository
import com.ecom.modules.users.domain.repository.IUserRepository

class InsertProductUseCase (
    private val productRepository: IProductRepository
){
    suspend operator fun invoke(product: Product){
      productRepository.insertProduct(product)
    }
}