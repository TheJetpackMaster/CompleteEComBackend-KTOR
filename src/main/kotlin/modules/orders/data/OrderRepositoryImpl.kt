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
        println("🔁 Starting placeOrder for userId: $userId")

        val cart = cartRepository.getCart(userId)
        println("🛒 Cart for user $userId: $cart")

        if (cart == null) {
            println("❌ Cart is null")
            return@dbQuery false
        }

        if (cart.items.isEmpty()) {
            println("❌ Cart is empty")
            return@dbQuery false
        }

        val products = productRepository.getAllProducts()
        println("📦 All products: ${products.map { it.id }}")

        val total = cart.items.sumOf { item ->
            val product = products.find { it.id == item.productId }
            if (product == null) {
                println("❌ Product not found for ID: ${item.productId}")
                return@dbQuery false
            }
            println("✅ Found product: ${product.id}, price: ${product.price}, qty: ${item.quantity}")
            product.price * item.quantity
        }

        println("💰 Total price calculated: $total")

        val orderId = UUID.randomUUID().toString()
        println("🆔 Generated order ID: $orderId")

        val inserted = OrdersTable.insert {
            it[id] = orderId
            it[OrdersTable.userId] = userId
            it[items] = Json.encodeToString(cart.items)
            it[totalPrice] = total.toDouble()
            it[status] = "PENDING"
            it[createdAt] = System.currentTimeMillis()
        }

        if (inserted.resultedValues != null) {
            println("✅ Order inserted successfully into DB")
            cartRepository.clearCart(userId)
            println("🧹 Cart cleared for userId: $userId")
            return@dbQuery true
        }

        println("❌ Insert failed: resultedValues is null")
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
            .selectAll()
            .where { (OrdersTable.id eq orderId) and (OrdersTable.userId eq userId) }
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
