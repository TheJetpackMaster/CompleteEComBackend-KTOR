package com.ecom.modules.auth.data

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import modules.users.domain.model.User

class JwtService(private val secret:String) {

    val algorithm = Algorithm.HMAC256(secret)
    val verifier = JWT.require(algorithm).build()



    // Generate Token
    fun generateToken(user: User): String = JWT.create()
        .withSubject("authentication")
        .withClaim("id",user.id)
        .withClaim("email",user.email)
        .withClaim("role", user.role.name)
        .sign(algorithm)


    // Verify token
    fun verifyToken(token:String): DecodedJWT = verifier.verify(token)

}