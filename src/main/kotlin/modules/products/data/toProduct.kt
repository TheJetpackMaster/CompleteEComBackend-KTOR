package modules.products.data

import com.ecom.modules.products.data.ProductTable
import com.ecom.modules.products.domain.model.Product
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toProduct() = Product(
    id = this[ProductTable.id],
    name = this[ProductTable.name],
    description = this[ProductTable.description],
    price = this[ProductTable.price],
    stock = this[ProductTable.stock],
    ownerId = this[ProductTable.ownerId]
)