package modules.cart.data

import com.ecom.modules.users.data.UsersTable
import com.ecom.modules.users.data.UsersTable.id
import org.jetbrains.exposed.sql.Table

object CartsTable : Table("carts") {
    val userId = varchar("user_id", 50).references(UsersTable.id)
    val items = text("items") // JSON string
    val updatedAt = long("updated_at")

    override val primaryKey = PrimaryKey(userId)
}