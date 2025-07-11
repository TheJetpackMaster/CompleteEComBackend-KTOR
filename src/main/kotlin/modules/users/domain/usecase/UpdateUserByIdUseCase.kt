package modules.users.domain.usecase

import com.ecom.modules.users.domain.repository.IUserRepository
import modules.users.domain.model.User
import java.util.UUID

class UpdateUserByIdUseCase(
    private val userRepository: IUserRepository
) {
    suspend operator fun invoke(id: UUID, updatedUser: User): User? {
        return userRepository.updateUserById(id, updatedUser)
    }
}