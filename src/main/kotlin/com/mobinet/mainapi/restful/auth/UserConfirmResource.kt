package com.mobinet.mainapi.restful.auth

import com.mobinet.mainapi.model.Directors
import com.mobinet.mainapi.service.auth.UserConfirmService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Component
@Path("confirm")
open class UserConfirmResource @Autowired constructor(val service: UserConfirmService, val Req: HttpServletRequest) {
    data class Request(val status: Response.Status, val message: String? = "", val token: String? = "")

    @Path("/email/{id}/{token}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun confirmEmail( @PathParam("id") id: Long, @PathParam("token") token: String): Response {
        val directors = Directors()
//        val request = Req
//        val token = request.getHeader("email-confirm")
        val resp = service.confirmEmail(id, token, directors)
        println("Confirm Resource: $id $token ")
        return when(resp) {
            is UserConfirmService.Resp.EmailConfirm -> Response.ok(Request(Response.Status.OK, "User confirmed")).build()
            is UserConfirmService.Resp.Success -> Response.ok(Request(Response.Status.OK, resp.msg)).build()
            is UserConfirmService.Resp.Error -> Response.status(Response.Status.BAD_REQUEST).build()
            else -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()
        }
    }
}