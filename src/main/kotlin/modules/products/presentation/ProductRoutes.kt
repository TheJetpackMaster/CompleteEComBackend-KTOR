package com.ecom.modules.products.presentation

import com.ecom.modules.products.domain.model.Product
import com.ecom.modules.products.domain.usecase.DeleteAllProductsByOwnerIdUseCase
import com.ecom.modules.products.domain.usecase.DeleteProductByIdUseCase
import com.ecom.modules.products.domain.usecase.FindProductByIdUseCase
import com.ecom.modules.products.domain.usecase.InsertProductUseCase
import com.ecom.modules.products.domain.usecase.UpdateProductByIdUseCase
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import modules.products.domain.model.CreateProductRequest
import modules.products.domain.usecase.GetAllProductsUseCase
import java.util.UUID

fun Route.productRoutes(
    insertProductUseCase: InsertProductUseCase,
    findProductByIdUseCase: FindProductByIdUseCase,
    getAllProductsUseCase: GetAllProductsUseCase,
    deleteProductUseCase: DeleteProductByIdUseCase,
    updateProductUseCase: UpdateProductByIdUseCase,
    deleteAllProductByOwnerIdUseCase: DeleteAllProductsByOwnerIdUseCase
) {
    route("/products") {

        authenticate("jwt") {
            // Create product
            post {
                val request = call.receive<CreateProductRequest>()

                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.getClaim("userId", String::class)
                    ?: return@post call.respond(HttpStatusCode.Unauthorized)

                val product = Product(
                    id = UUID.randomUUID().toString(),
                    name = request.name,
                    price = request.price,
                    description = request.description,
                    stock = request.stock,
                    ownerId = userId
                )

                insertProductUseCase(product)
                call.respond(HttpStatusCode.Created, product)
            }

            // Get product by ID
            get("/{id}") {
                val id =
                    call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing or invalid ID")

                val product = findProductByIdUseCase(id)

                if (product != null) {
                    call.respond(HttpStatusCode.OK, product)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Product not found")
                }
            }


            // Delete product by ID
            delete("/{id}") {
                val id = call.parameters["id"] ?: return@delete call.respond(
                    HttpStatusCode.BadRequest,
                    "Missing or invalid ID"
                )

                val deleted = deleteProductUseCase(id)
                if (deleted) {
                    call.respond(HttpStatusCode.OK, "Product deleted successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Product not found")
                }
            }

            // Update product by ID
            put("/{id}") {
                val id =
                    call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Missing or invalid ID")
                val request = call.receive<CreateProductRequest>()

                val updated = updateProductUseCase(
                    id,
                    Product(
                        id = id,
                        name = request.name,
                        price = request.price,
                        description = request.description,
                        stock = request.stock,
                        ownerId = "" // Optional: get from JWT if needed
                    ),
                )

                if (updated != null) {
                    call.respond(HttpStatusCode.OK, "Product updated successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Product not found")
                }
            }

            delete {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.getClaim("userId", String::class)
                    ?: return@delete call.respond(HttpStatusCode.Unauthorized)

                val deletedCount = deleteAllProductByOwnerIdUseCase(userId)

                call.respond(
                    HttpStatusCode.OK,
                    "$deletedCount products deleted for user $userId"
                )
            }

        }

        // Get all products
        get {
            val products = getAllProductsUseCase()
            call.respond(HttpStatusCode.OK, products)
        }
    }
}
