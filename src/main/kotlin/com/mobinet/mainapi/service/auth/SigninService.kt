package com.mobinet.mainapi.service.auth

import com.mobinet.mainapi.model.Directors
import com.mobinet.mainapi.repository.DirectorsRepository
import org.springframework.stereotype.Component

@Component
class SigninService(private val directorsRepository: DirectorsRepository) {
    interface Resp {
        class Success(val msg: String): Resp
        class Error(): Resp
    }

    fun signin(directors: Directors): Resp {
        return if (getUsernameBool(directors.username) && getUserPasswordBool(directors.password))
            Resp.Success("Success")
        else
            Resp.Error()
    }

    fun getUsernameBool(username: String): Boolean {
        return true
    }
    fun getUserPasswordBool(password: String?): Boolean {
        return true
    }
}