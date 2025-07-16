package modules.cart.data

import com.ecom.config.DatabaseFactory.dbQuery
import com.ecom.modules.users.data.UsersTable
import com.ecom.products.CartRepository
import kotlinx.serialization.json.Json
import modules.cart.domain.model.Cart
import modules.cart.domain.model.CartItem
import modules.cart.domain.repository.ICartRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

class CartRepositoryImpl : ICartRepository {


    override suspend fun getCart(userId: String): Cart? = dbQuery {
        CartsTable.selectAll()
            .where { CartsTable.userId eq userId }
            .firstOrNull()?.let {
                Cart(
                    userId = it[CartsTable.userId],
                    items = Json.decodeFromString(it[CartsTable.items])
                )
            }
    }


    override suspend fun addToCart(userId: String, productId: String, quantity: Int): Boolean = dbQuery {
        val existingCart = getCart(userId)

        val updatedItems = if (existingCart != null) {
            val items = existingCart.items.toMutableList()
            val index = items.indexOfFirst { it.productId == productId }
            if (index >= 0) {
                items[index] = items[index].copy(quantity = items[index].quantity + quantity)
            } else {
                items.add(CartItem(productId, quantity))
            }
            items
        } else {
            listOf(CartItem(productId, quantity))
        }

        val affectedRows: Int = if (existingCart == null) {
            // insert returns InsertStatement<Number>, so we treat success if not null
            val insertResult = CartsTable.insert {
                it[CartsTable.userId] = userId
                it[CartsTable.items] = Json.encodeToString(updatedItems)
                it[CartsTable.updatedAt] = System.currentTimeMillis()
            }
            if (insertResult.insertedCount > 0) 1 else 0
        } else {
            CartsTable.update({ CartsTable.userId eq userId }) {
                it[items] = Json.encodeToString(updatedItems)
                it[updatedAt] = System.currentTimeMillis()
            }
        }

        return@dbQuery affectedRows > 0
    }



    override suspend fun removeFromCart(userId: String, productId: String): Boolean = dbQuery {
        val cart = getCart(userId) ?: return@dbQuery false
        val updatedItems = cart.items.filter { it.productId != productId }
        CartsTable.update({ CartsTable.userId eq userId }) {
            it[items] = Json.encodeToString(updatedItems)
            it[updatedAt] = System.currentTimeMillis()
        } > 0
    }

    override suspend fun clearCart(userId: String): Boolean = dbQuery {
        CartsTable.deleteWhere { CartsTable.userId eq userId } > 0
    }
}
