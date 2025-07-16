package com.ecom.modules.cart.di

import modules.cart.data.CartRepositoryImpl
import modules.cart.domain.repository.ICartRepository
import modules.cart.domain.usecase.AddToCartUseCase
import modules.cart.domain.usecase.ClearCartUseCase
import modules.cart.domain.usecase.GetCartUseCase
import modules.cart.domain.usecase.RemoveFromCartUseCase
import org.koin.dsl.module

val cartModule = module {

    // Repo Binding
    single<ICartRepository> { CartRepositoryImpl() }

    // Use Cases
    single { AddToCartUseCase(get()) }
    single { RemoveFromCartUseCase(get()) }
    single { ClearCartUseCase(get()) }
    single { GetCartUseCase(get()) }

    // (Optional: Bind UserService later here if you add it)
}