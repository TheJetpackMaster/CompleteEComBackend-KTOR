package modules.orders.data

import com.ecom.modules.users.data.UsersTable
import org.jetbrains.exposed.sql.Table

object OrdersTable : Table("orders") {
    val id = varchar("id", 50)
    val userId = varchar("user_id", 50).references(UsersTable.id)
    val items = text("items") // JSON string of List<OrderItem>
    val totalPrice = double("total_price")
    val status = varchar("status", 20)
    val createdAt = long("created_at")

    override val primaryKey = PrimaryKey(id)
}