package com.mobinet.mainapi.restful.auth

import com.mobinet.mainapi.model.Directors
import com.mobinet.mainapi.service.auth.SigninService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Component
@Path("auth")
class SigninResource @Autowired constructor(val service: SigninService){
    data class Request(val status: Response.Status, val message: String, val oauthtoken: String? = "")

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun auth(directors: Directors):Response {
        val resp = service.signin(directors)
        return when(resp) {
            is SigninService.Resp.Success -> Response.ok(Request(Response.Status.OK, resp.msg, resp.oauthtoken)).build()
            is SigninService.Resp.Error -> Response.status(Response.Status.BAD_REQUEST).build()
            else -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()
        }
    }
}