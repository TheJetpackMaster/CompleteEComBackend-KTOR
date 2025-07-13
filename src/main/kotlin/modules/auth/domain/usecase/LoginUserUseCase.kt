package com.ecom.modules.auth.domain.usecase

import com.ecom.modules.auth.data.JwtService
import com.ecom.modules.users.domain.repository.IUserRepository
import modules.auth.domain.model.AuthResponse


class LoginUserUseCase(
    private val userRepository: IUserRepository,
    private val jwtService: JwtService
) {
    suspend operator fun invoke(email:String,password :String): AuthResponse? {
        val user = userRepository.findByEmail(email)?: return null

        if(user.password != password) return null
        val token = jwtService.generateToken(user)

        return AuthResponse(
            token = token,
            userId = user.id
        )
    }
}