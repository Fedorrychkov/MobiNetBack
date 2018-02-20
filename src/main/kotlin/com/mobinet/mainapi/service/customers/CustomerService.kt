package com.mobinet.mainapi.service.customers

import com.mobinet.mainapi.model.Customers
import com.mobinet.mainapi.repository.CustomersRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class CustomerService(private val customersRepository: CustomersRepository) {
    interface Resp {
        class Success(val msg: String, val requestBody: Customers?): Resp
        class GetAllSuccess(val requestBody: List<Customers>): Resp
        class GetOneSuccess(val requestBody: ResponseEntity<Customers>): Resp
        class Error: Resp
    }

    fun create(customers: Customers): Resp {
        println(customers)
        return if (!findBy(customers)) {
            Resp.Error()
        } else {
            createNewCustomer(customers)
            Resp.Success("Customer is created", null)
        }
    }

    fun getCustomers(directorId: Long): Resp {
        return Resp.GetAllSuccess(findAllByDirectorId(directorId))
    }


    fun getCustomer(id: Long): Resp {
        return Resp.GetOneSuccess(findById(id))
    }

    fun customersList(): List<Customers> = customersRepository.findAll()

    fun createNewCustomer(customers: Customers): Customers =
            customersRepository.save(customers)

    fun findAllByDirectorId(directorId: Long): List<Customers> {
        return customersRepository.findAllByDirectorId(directorId)
    }

    fun findById(id: Long): ResponseEntity<Customers> {
        return customersRepository.findById(id).map{ customer ->
            return ResponseEntity.ok(customer)
        }.getOrElse(id.toInt()){ ResponseEntity.noContent().build() }
    }

    fun findBy(customers: Customers): Boolean {
        if (!findByPhone(customers.phone) || !findByEmail(customers.email))
            return false
        else return true
    }
    fun findByPhone(phone: String?): Boolean {
        return customersRepository.findByPhone(phone).map {}.isEmpty()
    }
    fun findByEmail(email: String?): Boolean {
        return customersRepository.findByEmail(email).map {}.isEmpty()
    }
}