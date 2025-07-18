import modules.orders.data.OrderRepositoryImpl
import modules.orders.domain.repository.IOrderRepository
import modules.orders.domain.usecase.CancelOrderUseCase
import modules.orders.domain.usecase.GetOrderByIdUseCase
import modules.orders.domain.usecase.GetOrdersByUserUseCase
import modules.orders.domain.usecase.PlaceOrderUseCase
import org.koin.core.module.Module
import org.koin.dsl.module


val orderModule = module {
    single<IOrderRepository> { OrderRepositoryImpl(get(), get()) }

    single { PlaceOrderUseCase(get()) }
    single { GetOrdersByUserUseCase(get()) }
    single { CancelOrderUseCase(get()) }
    single { GetOrderByIdUseCase(get()) }
}