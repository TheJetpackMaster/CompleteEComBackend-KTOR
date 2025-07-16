package com.ecom.modules.cart.di

import com.ecom.modules.users.data.UserRepositoryImpl
import com.ecom.modules.users.domain.repository.IUserRepository
import com.ecom.modules.users.domain.usecase.RegisterUserUseCase
import modules.cart.data.CartRepositoryImpl
import modules.cart.domain.repository.ICartRepository
import modules.cart.domain.usecase.AddToCartUseCase
import modules.cart.domain.usecase.ClearCartUseCase
import modules.cart.domain.usecase.GetCartUseCase
import modules.cart.domain.usecase.RemoveFromCartUseCase
import modules.users.domain.usecase.DeleteUserByIdUseCase
import modules.users.domain.usecase.GetUserByEmailUseCase
import modules.users.domain.usecase.GetUserByIdUseCase
import modules.users.domain.usecase.UpdateUserByIdUseCase
import org.koin.dsl.module

val userModule = module {

    // Repo Binding
    single<ICartRepository> { CartRepositoryImpl() }

    // Use Cases
    single { AddToCartUseCase(get()) }
    single { RemoveFromCartUseCase(get()) }
    single { ClearCartUseCase(get()) }
    single { GetCartUseCase(get()) }

    // (Optional: Bind UserService later here if you add it)
}