package modules.orders.presentation

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import modules.cart.presentation.userIdFromToken
import modules.orders.domain.model.CancelOrderRequest
import modules.orders.domain.usecase.CancelOrderUseCase
import modules.orders.domain.usecase.GetOrderByIdUseCase
import modules.orders.domain.usecase.GetOrdersByUserUseCase
import modules.orders.domain.usecase.PlaceOrderUseCase

fun Route.orderRoutes(
    placeOrder: PlaceOrderUseCase,
    getOrdersByUser: GetOrdersByUserUseCase,
    cancelOrder: CancelOrderUseCase,
    getOrderById: GetOrderByIdUseCase
) {
    authenticate("jwt") {

        route("/orders") {

            post("/place") {
                val userId = call.userIdFromToken()
                val success = placeOrder(userId)
                call.respond(if (success) HttpStatusCode.OK else HttpStatusCode.BadRequest)
            }

            get{
                val userId = call.userIdFromToken()
                val orders = getOrdersByUser(userId)
                call.respond(orders)
            }

            post("cancel") {
                val userId = call.userIdFromToken()
                val cancelReq = call.receive<CancelOrderRequest>()
                val cancelled = cancelOrder(cancelReq.orderId, userId)
                call.respond(if (cancelled) HttpStatusCode.OK else HttpStatusCode.BadRequest)
            }

            get("/{id}") {
                val userId = call.userIdFromToken()
                val orderId =
                    call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing orderId")

                val order = getOrderById(orderId, userId)
                if (order != null) {
                    call.respond(order)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Order not found")
                }
            }
        }
    }
}