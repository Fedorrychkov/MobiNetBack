package com.mobinet.mainapi.restful.orders

import com.mobinet.mainapi.model.Orders
import com.mobinet.mainapi.service.orders.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Component
@Path("orders")
class OrderResource @Autowired constructor(val service: OrderService){
    data class Request(val status: Response.Status, val message: String, val requestBody: Orders?)
    data class Request2(val status: Response.Status, val requestBody: List<Orders>)
    data class Request3(val status: Response.Status, val requestBody: ResponseEntity<Orders>)

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun createOrder(order: Orders): Response {
        order.dateCreated = LocalDateTime.now().toString()
        if (order.status === "")
            order.status = "NEW_ORDER"
        val resp = service.create(order)
        return when(resp) {
            is OrderService.Resp.Success -> Response.ok(Request(Response.Status.OK, resp.msg, null)).build()
            is OrderService.Resp.Error -> Response.status(Response.Status.BAD_REQUEST).build()
            else -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()
        }
    }

    @GET
    @Path("/{directorId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun getOrders(@PathParam("directorId") directorId: Long): Response {
        val resp = service.getOrders(directorId)
        return when(resp) {
            is OrderService.Resp.GetAllSuccess -> Response.ok(Request2(Response.Status.OK, resp.requestBody)).build()
            is OrderService.Resp.Error -> Response.status(Response.Status.BAD_REQUEST).build()
            else -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()
        }
    }

    @GET
    @Path("/{directorId}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun getOrder(@PathParam("directorId") directorId: Long, @PathParam("id") id: Long): Response {
        val resp = service.getOrder(id)
        return when(resp) {
            is OrderService.Resp.GetOneSuccess -> Response.ok(Request3(Response.Status.OK, resp.requestBody)).build()
            is OrderService.Resp.Error -> Response.status(Response.Status.BAD_REQUEST).build()
            else -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()
        }
    }
}