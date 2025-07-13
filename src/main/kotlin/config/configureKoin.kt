package config

import io.ktor.server.application.*
import modules.auth.di.authModule
import modules.users.di.userModule
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(
            userModule,
            authModule
        )
    }
}