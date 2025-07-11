package modules.users.di

import com.ecom.modules.users.data.UserRepositoryImpl
import com.ecom.modules.users.domain.repository.IUserRepository
import com.ecom.modules.users.domain.usecase.RegisterUserUseCase
import modules.users.domain.usecase.DeleteUserByIdUseCase
import modules.users.domain.usecase.GetUserByEmailUseCase
import modules.users.domain.usecase.GetUserByIdUseCase
import modules.users.domain.usecase.UpdateUserByIdUseCase
import org.koin.dsl.module

val userModule = module {

    // Repo Binding
    single<IUserRepository> { UserRepositoryImpl() }

    // Use Cases
    single { RegisterUserUseCase(get()) }
    single { UpdateUserByIdUseCase(get()) }
    single { DeleteUserByIdUseCase(get()) }
    single { GetUserByEmailUseCase(get()) }
    single { GetUserByIdUseCase(get()) }

    // (Optional: Bind UserService later here if you add it)
}