package modules.users.domain.usecase

import com.ecom.modules.users.domain.repository.IUserRepository


class DeleteUserByIdUseCase(
    private val userRepository: IUserRepository
) {
    suspend operator fun invoke(id: String): Boolean {
        return userRepository.deleteUserById(id)
    }
}