package com.mobinet.mainapi.restful

import com.mobinet.mainapi.service.GreetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Component
@Path("greet/{name}")
open class GreetResource @Autowired constructor(val service: GreetService) {
    data class Greet(val message: String, val time: String)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun greet(@PathParam("name") name: String): Response {
        val resp = service.greet(name)
        return when(resp) {
            is GreetService.Resp.Success -> Response.ok(Greet(resp.msg, Date().toString())).build()
            is GreetService.Resp.Error -> Response.status(Response.Status.BAD_REQUEST).build()
            else -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()
        }
    }
}