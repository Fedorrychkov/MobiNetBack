package com.mobinet.mainapi.restful

import com.mobinet.mainapi.model.Article
import com.mobinet.mainapi.service.ArticleService
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
@Path("article/{name}")
open class ArticleResource @Autowired constructor(val service: ArticleService) {
    data class Greet(val message: List<Article>, val time: String)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun greet(@PathParam("name") name: String): Response {
        val resp = service.greet(name)
        return when(resp) {
            is ArticleService.Resp.Success -> Response.ok(Greet(resp.msg, Date().toString())).build()
            is ArticleService.Resp.Error -> Response.status(Response.Status.BAD_REQUEST).build()
            else -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()
        }
    }
}