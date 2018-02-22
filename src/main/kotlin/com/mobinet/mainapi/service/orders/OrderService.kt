package com.mobinet.mainapi.service.orders

import com.mobinet.mainapi.model.Orders
import com.mobinet.mainapi.repository.OrdersRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class OrderService(private val ordersRepository: OrdersRepository) {
    interface Resp {
        class Success(val msg: String, val requestBody: Orders?): Resp
        class GetAllSuccess(val requestBody: List<Orders>): Resp
        class GetOneSuccess(val requestBody: ResponseEntity<Orders>): Resp
        class Error: Resp
    }

    fun create(order: Orders): Resp {
        println(order)
        return if (order.customerId === 0.toLong() || order.directorId === 0.toLong() ) {
            Resp.Error()
        } else {
            createNewOrder(order)
            Resp.Success("Order is created", null)
        }
    }

    fun getOrders(directorId: Long): Resp {
        return Resp.GetAllSuccess(findAllByDirectorId(directorId))
    }


    fun getOrder(id: Long): Resp {
        return Resp.GetOneSuccess(findById(id))
    }

    fun findAllByDirectorId(directorId: Long): List<Orders> {
        return ordersRepository.findAllByDirectorId(directorId)
    }

    fun findById(id: Long): ResponseEntity<Orders> {
        return ordersRepository.findById(id).map{ order ->
            return ResponseEntity.ok(order)
        }.getOrElse(id.toInt()){ ResponseEntity.noContent().build() }
    }

    fun createNewOrder(order: Orders): Orders =
            ordersRepository.save(order)
}