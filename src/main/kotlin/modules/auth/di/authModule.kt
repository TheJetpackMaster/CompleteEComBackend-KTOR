package modules.auth.di

import com.ecom.modules.auth.data.JwtService
import com.ecom.modules.auth.domain.usecase.LoginUserUseCase
import com.ecom.modules.users.data.UserRepositoryImpl
import com.ecom.modules.users.domain.repository.IUserRepository
import org.koin.dsl.module

val authModule = module {

    single { JwtService("temp_secret_key") } // Replace with actual config later
    single { LoginUserUseCase(get(), get()) } // get() injects IUserRepository and JwtService

}