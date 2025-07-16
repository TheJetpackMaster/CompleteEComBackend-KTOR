package config



import com.ecom.config.configureDatabases
import com.ecom.config.configureHTTP
import com.ecom.config.configureMonitoring
import com.ecom.config.configureRouting
import com.ecom.config.configureSecurity
import com.ecom.config.configureSerialization
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

// Configure all the installing plugins
fun Application.configureKtor() {

    // Security config
    configureSecurity()

    // Koin config
    configureKoin()

    // Serialization config
    configureSerialization()

    // Database config
    configureDatabases()

    // Http config
    configureHTTP()

    // Monitoring config
    configureMonitoring()

    // Route config
    configureRouting()


}