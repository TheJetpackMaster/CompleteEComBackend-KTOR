package com.ecom.modules.products.domain.repository

import com.ecom.config.DatabaseFactory.dbQuery
import com.ecom.modules.products.data.ProductTable
import com.ecom.modules.products.domain.model.Product
import org.jetbrains.exposed.sql.insert

interface IProductRepository {

    // Add product
    suspend fun insertProduct(product: Product): Product

    // Delete all products
    suspend fun deleteAllProducts(ownerId:String): Boolean

    // Delete product by id
    suspend fun deleteProductById(productId: String): Boolean

    // Find Product by id
    suspend fun findProductById(productId:String):Product?

    // Update product by id
    suspend fun updateProductById(productId:String,updatedProduct: Product):Product?
}