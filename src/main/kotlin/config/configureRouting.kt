package com.ecom.config

import com.ecom.modules.auth.domain.usecase.LoginUserUseCase
import com.ecom.modules.auth.presentation.authRoutes
import com.ecom.modules.products.domain.usecase.DeleteAllProductsByOwnerIdUseCase
import com.ecom.modules.products.domain.usecase.DeleteProductByIdUseCase
import com.ecom.modules.products.domain.usecase.FindProductByIdUseCase
import com.ecom.modules.products.domain.usecase.InsertProductUseCase
import com.ecom.modules.products.domain.usecase.UpdateProductByIdUseCase
import com.ecom.modules.products.presentation.productRoutes
import com.ecom.modules.users.domain.usecase.RegisterUserUseCase
import com.ecom.modules.users.presentation.userRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*
import modules.cart.domain.usecase.AddToCartUseCase
import modules.cart.domain.usecase.ClearCartUseCase
import modules.cart.domain.usecase.GetCartUseCase
import modules.cart.domain.usecase.RemoveFromCartUseCase
import modules.cart.presentation.cartRoutes
import modules.products.domain.usecase.GetAllProductsUseCase
import modules.users.domain.usecase.GetUserByIdUseCase
import org.koin.ktor.ext.get

fun Application.configureRouting(

) {
    //User
    val registerUserUseCase: RegisterUserUseCase = get()
    val getUserByIdUseCase: GetUserByIdUseCase = get()

    //Auth
    val loginUserUseCase: LoginUserUseCase = get()

    //Product
    val insertProductUseCase: InsertProductUseCase = get()
    val findProductByIdUseCase: FindProductByIdUseCase = get()
    val getAllProductsUseCase: GetAllProductsUseCase = get()
    val deleteProductUseCase: DeleteProductByIdUseCase = get()
    val updateProductUseCase: UpdateProductByIdUseCase = get()
    val deleteAllProductByOwnerIdUseCase: DeleteAllProductsByOwnerIdUseCase = get()


    // Cart
    val addToCartUseCase: AddToCartUseCase = get()
    val removeFromCartUseCase: RemoveFromCartUseCase = get()
    val getCartUseCase: GetCartUseCase = get()
    val clearCartUseCase: ClearCartUseCase = get()

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

        // Product Routes
        productRoutes(
            insertProductUseCase,
            findProductByIdUseCase,
            getAllProductsUseCase,
            deleteProductUseCase,
            updateProductUseCase,
            deleteAllProductByOwnerIdUseCase
        )

        //Cart Routes
        cartRoutes(
            addToCartUseCase,
            removeFromCartUseCase,
            getCartUseCase,
            clearCartUseCase
        )
    }
}
