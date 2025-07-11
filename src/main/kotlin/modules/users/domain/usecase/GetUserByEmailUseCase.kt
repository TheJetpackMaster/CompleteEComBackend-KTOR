package modules.users.domain.usecase

import com.ecom.modules.users.domain.repository.IUserRepository
import modules.users.domain.model.User

class GetUserByEmailUseCase(
    private val userRepository: IUserRepository
) {
    suspend operator fun invoke(email: String): User? {
        return userRepository.findByEmail(email)
    }
}