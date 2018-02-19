package com.mobinet.mainapi.restful.customers

import com.mobinet.mainapi.model.Customers
import com.mobinet.mainapi.service.customers.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Component
@Path("customers")
open class CustomerResource @Autowired constructor(val service: CustomerService){
    data class Request(val status: Response.Status, val message: String, val requestBody: Customers?)

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun createCustomer(customers: Customers): Response {
        customers.dateCreated = LocalDateTime.now().toString()
        customers.status = "STANDART_CUSTOMER"
        val resp = service.create(customers)
        return when(resp) {
            is CustomerService.Resp.Success -> Response.ok(Request(Response.Status.OK, resp.msg, null)).build()
            is CustomerService.Resp.Error -> Response.status(Response.Status.BAD_REQUEST).build()
            else -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()
        }
    }
}