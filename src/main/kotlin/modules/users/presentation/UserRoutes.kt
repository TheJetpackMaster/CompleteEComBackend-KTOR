package com.ecom.modules.users.presentation

import com.ecom.modules.users.domain.usecase.RegisterUserUseCase
import core.model.Role
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import modules.users.domain.model.RegisterRequest
import modules.users.domain.model.User
import modules.users.domain.usecase.GetUserByIdUseCase
import org.koin.ktor.ext.get
import java.util.UUID
import kotlin.random.Random


fun Route.userRoutes(
    registerUserUseCase: RegisterUserUseCase,
    getUserByIdUseCase: GetUserByIdUseCase
) {
    route("/users") {
        post("/register") {
            val request = call.receive<RegisterRequest>()

            val user = registerUserUseCase(
                User(
                    name = request.name,
                    email = request.email,
                    password = request.password,
                    role = Role.USER,
                    isVerifiedSeller = false
                )
            )

            call.respond(HttpStatusCode.Created, user)
        }


        get("/{id}") {
            val id = call.parameters["id"]

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Missing user ID")
                return@get
            }

            val user = getUserByIdUseCase(id)

            if (user == null) {
                call.respond(HttpStatusCode.NotFound, "User not found")
            } else {
                call.respond(HttpStatusCode.OK, user)
            }
        }

    }
}