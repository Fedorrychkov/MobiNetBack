package com.mobinet.mainapi.service.auth

import com.mobinet.mainapi.model.Directors
import com.mobinet.mainapi.repository.DirectorsRepository
import org.springframework.stereotype.Component

/** for web token **/
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.crypto.MacProvider
import java.security.Key

@Component
class SignupService(private val directorsRepository: DirectorsRepository ) {
    interface Resp {
        class Success(val msg: String): Resp
        class Error(): Resp
        class SaveDirector(val d: Directors): Resp
        class HaveUser(val msg: String): Resp
    }
    fun signup(directors: Directors): Resp {
        return  if (directors.username == "")
            Resp.Error()
        else if (!findBy(directors))
            Resp.HaveUser("Email or Username has already been taken!")
        else {
            val tokenGen: String = "" + directors.email
            var key: Key = MacProvider.generateKey()

            var compactJws = Jwts.builder()
                    .setSubject(tokenGen)
                    .signWith(SignatureAlgorithm.HS512, key)
                    .compact()
            directors.token = compactJws

            Resp.SaveDirector(createNewDirector(directors))
        }
    }

    fun createNewDirector(directors: Directors): Directors =
            directorsRepository.save(directors)

    fun findBy(directors: Directors): Boolean {
        if (!findByUsername(directors.username) || !findByEmail(directors.email))
            return false
         else return true
    }


    fun findByUsername(directorUsername: String): Boolean {
        return directorsRepository.findByUsername(directorUsername).map {}.isEmpty()
    }

    fun findByEmail(directorEmail: String?): Boolean {
        return directorsRepository.findByEmail(directorEmail).map {}.isEmpty()
    }
}
