package com.mobinet.mainapi.restful.auth

import com.mobinet.mainapi.model.Directors
import com.mobinet.mainapi.service.auth.SignupService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Component
@Path("signup")
open class SignupResource @Autowired constructor(val service: SignupService) {
    data class Request(val status: Response.Status, val message: String, val token: String? = "")

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun signup(directors: Directors): Response {
        directors.dateCreated = LocalDateTime.now().toString()
        directors.userConfirmed = false
        val resp = service.signup(directors)
        return when(resp) {
            is SignupService.Resp.Success -> Response.ok(Request(Response.Status.OK, resp.msg)).build()
            is SignupService.Resp.HaveUser -> Response.ok(Request(Response.Status.BAD_REQUEST, resp.msg)).build()
            is SignupService.Resp.SaveDirector -> Response.ok(Request(Response.Status.OK, "User created")).build()
            is SignupService.Resp.Error -> Response.status(Response.Status.BAD_REQUEST).build()
            else -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()
        }
    }
}