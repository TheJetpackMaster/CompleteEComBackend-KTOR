package modules.products.di

import com.ecom.modules.products.data.ProductRepositoryImpl
import com.ecom.modules.products.domain.repository.IProductRepository
import com.ecom.modules.products.domain.usecase.DeleteAllProductsByOwnerIdUseCase
import com.ecom.modules.products.domain.usecase.DeleteProductByIdUseCase
import com.ecom.modules.products.domain.usecase.FindProductByIdUseCase
import com.ecom.modules.products.domain.usecase.InsertProductUseCase
import com.ecom.modules.products.domain.usecase.UpdateProductByIdUseCase
import modules.products.domain.usecase.GetAllProductsUseCase
import org.koin.dsl.module

val productModule = module {

    // Repo Binding
    single<IProductRepository> { ProductRepositoryImpl() }

//    // Use Cases
    single { InsertProductUseCase(get()) }
    single { UpdateProductByIdUseCase(get()) }
    single { DeleteProductByIdUseCase(get()) }
    single { DeleteAllProductsByOwnerIdUseCase(get()) }
    single { FindProductByIdUseCase(get()) }

    single { GetAllProductsUseCase(get()) }

    // (Optional: Bind UserService later here if you add it)
}