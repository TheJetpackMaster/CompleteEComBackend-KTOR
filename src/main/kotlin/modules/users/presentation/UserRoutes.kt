package com.ecom.modules.users.presentation

import com.ecom.modules.users.domain.usecase.RegisterUserUseCase
import core.model.Role
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import modules.users.domain.model.RegisterRequest
import modules.users.domain.model.User
import java.util.UUID
import kotlin.random.Random

fun Route.userRoutes(
    registerUserUseCase: RegisterUserUseCase
) {
    route("/users") {
        post("/register") {
            val request = call.receive<RegisterRequest>()

            val user = registerUserUseCase(
                User(
                    id = UUID.randomUUID(),
                    name = request.name,
                    email = request.email,
                    password = request.password,
                    role = Role.USER,
                    isVerifiedSeller = false
                )
            )

            call.respond(HttpStatusCode.Created, user)
        }
    }
}