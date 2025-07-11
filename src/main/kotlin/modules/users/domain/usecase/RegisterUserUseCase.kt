package com.ecom.modules.users.domain.usecase

import com.ecom.modules.users.domain.repository.IUserRepository
import modules.users.domain.model.User

class RegisterUserUseCase(
    private val userRepository: IUserRepository
) {
    suspend operator fun invoke(user: User): User {
        return userRepository.save(user)
    }
}