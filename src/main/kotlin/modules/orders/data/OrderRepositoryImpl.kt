package modules.orders.data

import com.ecom.config.DatabaseFactory.dbQuery
import com.ecom.modules.products.domain.repository.IProductRepository
import kotlinx.serialization.json.Json
import modules.cart.domain.repository.ICartRepository
import modules.orders.domain.model.Order
import modules.orders.domain.repository.IOrderRepository
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import java.util.UUID

class OrderRepositoryImpl(
    private val cartRepository: ICartRepository,
    private val productRepository: IProductRepository
) : IOrderRepository {

    override suspend fun placeOrder(userId: String): Boolean = dbQuery {
        val cart = cartRepository.getCart(userId) ?: return@dbQuery false
        if (cart.items.isEmpty()) return@dbQuery false

        val products = productRepository.getAllProducts() // or individual checks
        val total = cart.items.sumOf { item ->
            val product = products.find { it.id == item.productId } ?: return@dbQuery false
            product.price * item.quantity
        }.toDouble()

        val orderId = UUID.randomUUID().toString()

        val inserted = OrdersTable.insert {
            it[id] = orderId
            it[OrdersTable.userId] = userId
            it[items] = Json.encodeToString(cart.items)
            it[totalPrice] = total
            it[status] = "PENDING"
            it[createdAt] = System.currentTimeMillis()
        }

        if (inserted.resultedValues != null) {
            cartRepository.clearCart(userId)
            return@dbQuery true
        }

        false
    }

    override suspend fun getOrdersByUser(userId: String): List<Order> = dbQuery {
        OrdersTable.selectAll()
            .where{ OrdersTable.userId eq userId }
            .map {
                Order(
                    id = it[OrdersTable.id],
                    userId = it[OrdersTable.userId],
                    items = Json.decodeFromString(it[OrdersTable.items]),
                    totalPrice = it[OrdersTable.totalPrice],
                    status = it[OrdersTable.status],
                    createdAt = it[OrdersTable.createdAt]
                )
            }
    }

    override suspend fun cancelOrder(orderId: String, userId: String): Boolean = dbQuery {
        OrdersTable.update({
            OrdersTable.id eq orderId and (OrdersTable.userId eq userId)
        }) {
            it[status] = "CANCELLED"
        } > 0
    }

    override suspend fun getOrderById(orderId: String, userId: String): Order? = dbQuery {
        OrdersTable
            .select { (OrdersTable.id eq orderId) and (OrdersTable.userId eq userId) }
            .mapNotNull {
                Order(
                    id = it[OrdersTable.id],
                    userId = it[OrdersTable.userId],
                    items = Json.decodeFromString(it[OrdersTable.items]),
                    totalPrice = it[OrdersTable.totalPrice],
                    status = it[OrdersTable.status],
                    createdAt = it[OrdersTable.createdAt]
                )
            }
            .singleOrNull()
    }

}
