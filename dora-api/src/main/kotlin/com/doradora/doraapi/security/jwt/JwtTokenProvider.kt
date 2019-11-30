package com.doradora.doraapi.security.jwt

import com.doradora.doraapi.data.property.JwtProperty
import com.doradora.doraapi.security.UserDetail
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtTokenProvider(private val jwtProperty: JwtProperty) {
    fun getUsernameFrom(token: String): String = getClaimFrom(token, Claims::getSubject)

    fun getExpirationFrom(token: String): Date = getClaimFrom(token, Claims::getExpiration)

    private fun <T> getClaimFrom(token: String, action: (Claims) -> T): T = action(getAllClaimsFrom(token))

    private fun getAllClaimsFrom(token: String): Claims =
        Jwts.parser().setSigningKey(jwtProperty.secret).parseClaimsJws(token).body

    fun generateToken(userDetail: UserDetails): String =
        Jwts.builder()
            .setSubject(userDetail.username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtProperty.expiry))
            .signWith(SignatureAlgorithm.HS512, jwtProperty.secret)
            .compact()

    fun isValid(token: String, userDetail: UserDetails): Boolean =
        getUsernameFrom(token) == userDetail.username && !isTokenExpired(token)

    fun isTokenExpired(token: String): Boolean = getExpirationFrom(token).before(Date())
}