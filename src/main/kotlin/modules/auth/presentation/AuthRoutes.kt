package com.ecom.modules.auth.presentation

import com.ecom.modules.auth.domain.usecase.LoginUserUseCase
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import modules.auth.domain.model.AuthRequest

fun Route.authRoutes(
    loginUserUseCase: LoginUserUseCase
){
    route("/auth"){
        post("/login"){
            val request = call.receive<AuthRequest>()
            val response = loginUserUseCase(request.email,request.password)

            if(response == null){
                call.respond(HttpStatusCode.Unauthorized,"invalid credentials")
            }else{
                call.respond(HttpStatusCode.OK,response)
            }
        }
    }
}