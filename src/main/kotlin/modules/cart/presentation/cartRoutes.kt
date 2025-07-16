package modules.cart.presentation

import com.sun.tools.jdeprscan.Main.call
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.UnauthorizedResponse
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import modules.cart.domain.model.Cart
import modules.cart.domain.model.CartItem
import modules.cart.domain.model.RemoveItemRequest
import modules.cart.domain.usecase.AddToCartUseCase
import modules.cart.domain.usecase.ClearCartUseCase
import modules.cart.domain.usecase.GetCartUseCase
import modules.cart.domain.usecase.RemoveFromCartUseCase
import kotlin.reflect.KClass

fun Route.cartRoutes(
    addToCart: AddToCartUseCase,
    removeFromCart: RemoveFromCartUseCase,
    getCart: GetCartUseCase,
    clearCart: ClearCartUseCase
) {

    authenticate("jwt") {
        get("/cart") {
            val userId = call.userIdFromToken()
            val cart = getCart(userId)
            call.respond(cart ?: Cart(userId, emptyList()))
        }

        post("/cart/add") {
            val userId = call.userIdFromToken()
            val item = call.receive<CartItem>()
            val success = addToCart(userId, item.productId, item.quantity)
            call.respond(if (success) HttpStatusCode.OK else HttpStatusCode.InternalServerError)
        }

        post("/cart/remove") {
            val userId = call.userIdFromToken()
            val req = call.receive<RemoveItemRequest>()
            val success = removeFromCart(userId, req.productId)
            call.respond(if (success) HttpStatusCode.OK else HttpStatusCode.InternalServerError)
        }

        delete("/cart/clear") {
            val userId = call.userIdFromToken()
            val cleared = clearCart(userId)
            call.respond(if (cleared) HttpStatusCode.OK else HttpStatusCode.InternalServerError)
        }
    }
}


fun ApplicationCall.userIdFromToken(): String {
    return principal<JWTPrincipal>()?.getClaim("userId", String::class)
        ?: throw UnauthorizedException()
}
class UnauthorizedException : RuntimeException("Unauthorized")