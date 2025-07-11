package modules.users.domain.usecase

import com.ecom.modules.users.domain.repository.IUserRepository
import modules.users.domain.model.User
import java.util.UUID

class GetUserByIdUseCase(
    private val userRepository: IUserRepository
) {
    suspend operator fun invoke(id: UUID): User? {
        return userRepository.findById(id)
    }
}