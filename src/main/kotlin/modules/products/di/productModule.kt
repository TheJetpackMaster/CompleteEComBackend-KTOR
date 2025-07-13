package modules.products.di

import com.ecom.modules.products.data.ProductRepositoryImpl
import com.ecom.modules.products.domain.repository.IProductRepository
import org.koin.dsl.module

val productModule = module {

    // Repo Binding
    single<IProductRepository> { ProductRepositoryImpl() }

//    // Use Cases
//    single { RegisterUserUseCase(get()) }
//    single { UpdateUserByIdUseCase(get()) }
//    single { DeleteUserByIdUseCase(get()) }
//    single { GetUserByEmailUseCase(get()) }
//    single { GetUserByIdUseCase(get()) }

    // (Optional: Bind UserService later here if you add it)
}