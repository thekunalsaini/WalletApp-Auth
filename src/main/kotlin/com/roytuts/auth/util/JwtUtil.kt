package com.roytuts.auth.util


import com.roytuts.auth.exception.JwtTokenMalformedException
import com.roytuts.auth.exception.JwtTokenMissingException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.util.*


@Component
@PropertySource("classpath:application.properties")
@Configuration
class JwtUtil {
    @Value("\${jwt.secret}")
    private val jwtSecret: String? = null

    @Value("\${jwt.token.validity}")
    private val tokenValidity: Long = 0

    fun getClaims(token: String?): Claims? {
        try {
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody()
        } catch (e: Exception) {
            println(e.message + " => " + e)
        }
        return null
    }

    fun generateToken(id: String?): String {
        val claims: Claims = Jwts.claims().setSubject(id)
        val nowMillis = System.currentTimeMillis()
        val expMillis = nowMillis + tokenValidity
        val exp = Date(expMillis)
        return Jwts.builder().setClaims(claims).setIssuedAt(Date(nowMillis)).setExpiration(exp)
            .signWith(SignatureAlgorithm.HS512, jwtSecret).compact()
    }


    @Throws(JwtTokenMalformedException::class, JwtTokenMissingException::class)
    fun validateToken(token: String?) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
        } catch (ex: SignatureException) {
            throw JwtTokenMalformedException("Invalid JWT signature")
        } catch (ex: MalformedJwtException) {
            throw JwtTokenMalformedException("Invalid JWT token")
        } catch (ex: ExpiredJwtException) {
            throw JwtTokenMalformedException("Expired JWT token")
        } catch (ex: UnsupportedJwtException) {
            throw JwtTokenMalformedException("Unsupported JWT token")
        } catch (ex: IllegalArgumentException) {
            throw JwtTokenMissingException("JWT claims string is empty.")
        }
    }
}