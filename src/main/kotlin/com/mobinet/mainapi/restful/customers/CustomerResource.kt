package com.mobinet.mainapi.restful.customers

import com.mobinet.mainapi.model.Customers
import com.mobinet.mainapi.service.customers.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Component
@Path("customers")
open class CustomerResource @Autowired constructor(val service: CustomerService){
    data class Request(val status: Response.Status, val message: String, val requestBody: Customers?)
    data class Request2(val status: Response.Status, val requestBody: List<Customers>)
    data class Request3(val status: Response.Status, val requestBody: ResponseEntity<Customers>)

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun createCustomer(customers: Customers): Response {
        customers.dateCreated = LocalDateTime.now().toString()
        if (customers.status === "")
            customers.status = "STANDART_CUSTOMER"
        val resp = service.create(customers)
        return when(resp) {
            is CustomerService.Resp.Success -> Response.ok(Request(Response.Status.OK, resp.msg, null)).build()
            is CustomerService.Resp.Error -> Response.status(Response.Status.BAD_REQUEST).build()
            else -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()
        }
    }

    @GET
    @Path("/{directorId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun getCustomers(@PathParam("directorId") directorId: Long): Response {
        val resp = service.getCustomers(directorId)
        return when(resp) {
            is CustomerService.Resp.GetAllSuccess -> Response.ok(Request2(Response.Status.OK, resp.requestBody)).build()
            is CustomerService.Resp.Error -> Response.status(Response.Status.BAD_REQUEST).build()
            else -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()
        }
    }

    @GET
    @Path("/{directorId}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun getCustomer(@PathParam("directorId") directorId: Long, @PathParam("id") id: Long): Response {
        val resp = service.getCustomer(id)
        return when(resp) {
            is CustomerService.Resp.GetOneSuccess -> Response.ok(Request3(Response.Status.OK, resp.requestBody)).build()
            is CustomerService.Resp.Error -> Response.status(Response.Status.BAD_REQUEST).build()
            else -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()
        }
    }
}