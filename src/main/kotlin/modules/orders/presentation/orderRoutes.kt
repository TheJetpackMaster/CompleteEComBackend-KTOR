package modules.orders.presentation

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import modules.cart.presentation.userIdFromToken
import modules.orders.domain.model.CancelOrderRequest
import modules.orders.domain.usecase.CancelOrderUseCase
import modules.orders.domain.usecase.GetOrdersByUserUseCase
import modules.orders.domain.usecase.PlaceOrderUseCase

fun Route.orderRoutes(
    placeOrder: PlaceOrderUseCase,
    getOrdersByUser: GetOrdersByUserUseCase,
    cancelOrder: CancelOrderUseCase
) {
    authenticate("jwt") {

        post("/orders/place") {
            val userId = call.userIdFromToken()
            val success = placeOrder(userId)
            call.respond(if (success) HttpStatusCode.OK else HttpStatusCode.BadRequest)
        }

        get("/orders") {
            val userId = call.userIdFromToken()
            val orders = getOrdersByUser(userId)
            call.respond(orders)
        }

        post("/orders/cancel") {
            val userId = call.userIdFromToken()
            val cancelReq = call.receive<CancelOrderRequest>()
            val cancelled = cancelOrder(cancelReq.orderId, userId)
            call.respond(if (cancelled) HttpStatusCode.OK else HttpStatusCode.BadRequest)
        }
    }
}