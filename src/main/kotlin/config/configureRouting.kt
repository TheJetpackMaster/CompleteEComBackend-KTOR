package com.ecom.config

import com.ecom.modules.auth.domain.usecase.LoginUserUseCase
import com.ecom.modules.auth.presentation.authRoutes
import com.ecom.modules.users.domain.usecase.RegisterUserUseCase
import com.ecom.modules.users.presentation.userRoutes
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.ValidationResult
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modules.users.domain.usecase.GetUserByIdUseCase
import org.koin.ktor.ext.get

fun Application.configureRouting(

) {
    //User
    val registerUserUseCase: RegisterUserUseCase = get()
    val getUserByIdUseCase: GetUserByIdUseCase = get()

    //Auth
    val loginUserUseCase: LoginUserUseCase = get()

    routing {

        //User routes
        userRoutes(
            registerUserUseCase,
            getUserByIdUseCase
        )

        //Auth Routes
        authRoutes(
            loginUserUseCase
        )

    }
}
