package modules.payments.data

import org.jetbrains.exposed.sql.Table

object PaymentsTable : Table("payments") {
    val id = varchar("id", 36)
    val orderId = varchar("order_id", 36)
    val userId = varchar("user_id", 36)
    val amount = double("amount")
    val status = varchar("status", 20) // Store enum as string
    val createdAt = long("created_at")

    override val primaryKey = PrimaryKey(id)
}
