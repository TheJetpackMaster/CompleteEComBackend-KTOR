package com.ecom


import com.ecom.config.configureDatabases
import com.ecom.config.configureHTTP
import com.ecom.config.configureMonitoring
import com.ecom.config.configureRouting
import com.ecom.config.configureSecurity
import com.ecom.config.configureSerialization
import config.configureKtor
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    // Configure all the installed frameworks(plugins)
    configureKtor()

}
