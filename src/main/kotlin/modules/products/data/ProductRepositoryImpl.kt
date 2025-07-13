package com.ecom.modules.products.data

import com.ecom.config.DatabaseFactory.dbQuery
import com.ecom.modules.products.domain.model.Product
import com.ecom.modules.products.domain.repository.IProductRepository
import modules.products.data.toProduct
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update


class ProductRepositoryImpl : IProductRepository {

    // Insert product
    override suspend fun insertProduct(product: Product): Product = dbQuery {
        ProductTable.insert {
            it[id] = product.id
            it[name] = product.name
            it[description] = product.description
            it[price] = product.price
            it[stock] = product.stock
            it[ownerId] = product.ownerId
        }

        return@dbQuery product
    }

    // Delete All products for specific seller
    override suspend fun deleteAllProducts(ownerId: String): Boolean = dbQuery {
        ProductTable.deleteWhere { ProductTable.ownerId eq ownerId } > 0
    }

    // Delete product by id
    override suspend fun deleteProductById(productId: String): Boolean = dbQuery {
        ProductTable.deleteWhere { ProductTable.id eq productId } > 0
    }


    // Find product by id
    override suspend fun findProductById(productId: String): Product? = dbQuery {
        ProductTable
            .selectAll()
            .where { ProductTable.id eq productId }
            .map { it.toProduct() }
            .singleOrNull()

        return@dbQuery null
    }

    // Update product by id
    override suspend fun updateProductById(
        productId: String,
        updatedProduct: Product
    ): Product? = dbQuery {
        ProductTable.update ({ ProductTable.id eq productId }){
            it[name] = updatedProduct.name
            it[description] = updatedProduct.description
            it[price] = updatedProduct.price
            it[stock] = updatedProduct.stock
            it[ownerId] = updatedProduct.ownerId

        }

        // Return updated product
        return@dbQuery findProductById(productId)

    }
}