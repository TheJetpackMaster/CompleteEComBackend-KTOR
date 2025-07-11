package modules.users.domain.usecase

import com.ecom.modules.users.domain.repository.IUserRepository
import java.util.UUID

class DeleteUserByIdUseCase(
    private val userRepository: IUserRepository
) {
    suspend operator fun invoke(id: UUID): Boolean {
        return userRepository.deleteUserById(id)
    }
}