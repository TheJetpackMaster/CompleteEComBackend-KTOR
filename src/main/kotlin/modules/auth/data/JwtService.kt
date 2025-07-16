package com.ecom.modules.auth.data

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.ecom.config.jwtSecret
import modules.users.domain.model.User

class JwtService(private val secret:String) {

    val algorithm = Algorithm.HMAC256(jwtSecret)
    val verifier = JWT.require(algorithm).build()


    // Generate Token
    fun generateToken(user: User): String = JWT.create()
        .withSubject("authentication")
        .withAudience("jwt-audience") // must match `jwtAudience` from config
        .withIssuer("https://jwt-provider-domain/") // must match `jwtDomain`
        .withClaim("userId", user.id)
        .withClaim("email", user.email)
        .withClaim("role", user.role.name)
        .sign(algorithm)

    // Verify token
    fun verifyToken(token:String): DecodedJWT = verifier.verify(token)

}