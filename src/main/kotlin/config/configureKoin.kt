package config

import com.ecom.modules.cart.di.cartModule
import io.ktor.server.application.*
import modules.auth.di.authModule
import modules.products.di.productModule
import modules.users.di.userModule
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(
            modules = listOf(
                userModule,
                authModule,
                productModule,
                cartModule
            )
        )
    }
}