package com.mobinet.mainapi.service.auth

import com.mobinet.mainapi.model.Directors
import com.mobinet.mainapi.repository.DirectorsRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.impl.crypto.MacProvider
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.security.Key

@Component
class UserConfirmService(private val directorsRepository: DirectorsRepository) {
    interface Resp {
        class Success(val msg: String): Resp
        class Error(): Resp
        class EmailConfirm(val confirm: Boolean): Resp
    }
    fun confirmEmail(id: Long, token: String, directors: Directors): Resp {

        val key: Key = MacProvider.generateKey()
        val confirmUser: Boolean = confirmBy(id, token, directors)

        if (confirmUser) {
            updateConfirmStatus(id, directors)
            return Resp.Success("User confirmed")
        } else
            return Resp.Error()
    }

    fun confirmBy(id: Long, token: String, directors: Directors): Boolean {
        val director: ResponseEntity<Directors> = getDirectorById(id)
        println("Confirm Service $director \n ${director.body.token == token} \n \n BD token: ${director.body.token} token: $token")

        return director.body.token == token
    }

    fun updateConfirmStatus(id: Long, directors: Directors): ResponseEntity<Directors> {
        return directorsRepository.findById(id).map{ existingDirector ->
            directors.userConfirmed = true
            val updateDirector: Directors = existingDirector
                    .copy(userConfirmed = directors.userConfirmed)
            ResponseEntity.ok().body(directorsRepository.save(updateDirector))
        }.getOrElse(id.toInt()){ ResponseEntity.notFound().build() }
    }

    fun getDirectorById(id: Long): ResponseEntity<Directors> {
        return directorsRepository.findById(id).map{ director ->
            return ResponseEntity.ok(director)
        }.getOrElse(id.toInt()){ ResponseEntity.noContent().build() }
    }
}
