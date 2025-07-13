package com.ecom.modules.products.data

import com.ecom.modules.users.data.UsersTable
import org.jetbrains.exposed.sql.Table


object ProductTable : Table("products") {
    val id = varchar("id",36)
    val name = varchar("name",50)
    val description = text("description")
    val price = integer("price")
    val stock = integer("stock")
    val ownerId = varchar("ownerId",36).references(UsersTable.id)
}