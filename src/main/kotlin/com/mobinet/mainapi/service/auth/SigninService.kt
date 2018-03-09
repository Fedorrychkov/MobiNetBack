package com.mobinet.mainapi.service.auth

import com.mobinet.mainapi.model.Directors
import com.mobinet.mainapi.repository.DirectorsRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.crypto.MacProvider
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.security.Key

@Component
class SigninService(private val directorsRepository: DirectorsRepository) {
    interface Resp {
        class Success(val msg: String, val oauthtoken: String): Resp
        class Error(): Resp
    }

    fun signin(directors: Directors): Resp {
        return if (getUsernameBool(directors.username) && getUserPasswordBool(directors.password)) {
            val tokenGen: String = "" + directors.username + directors.password
            var key: Key = MacProvider.generateKey()

            var compactJws = Jwts.builder()
                    .setSubject(tokenGen)
                    .signWith(SignatureAlgorithm.HS512, key)
                    .compact()
            updateOAuthToken(compactJws, directors)
            Resp.Success("Success", compactJws)
        } else
            Resp.Error()
    }

    fun getUsernameBool(username: String): Boolean {
        return !directorsRepository.findByUsername(username).map {}.isEmpty()
    }
    fun getUserPasswordBool(password: String?): Boolean {
        return !directorsRepository.findByPassword(password).map {}.isEmpty()
    }
    fun updateOAuthToken(token: String, directors: Directors): ResponseEntity<Directors> {
        val username: String = directors.username
        return directorsRepository.findByUsername(username).map{ existingDirector ->
            directors.OAuthtoken = token
            val updateDirector: Directors = existingDirector
                    .copy(OAuthtoken = directors.OAuthtoken)
            ResponseEntity.ok().body(directorsRepository.save(updateDirector))
        }.getOrElse(directors.id.toInt()){ ResponseEntity.notFound().build() }
    }
    fun checkOAuthToken(token: String): Boolean {
        return !directorsRepository.findByOAuthtoken(token).map {}.isEmpty()
    }
}