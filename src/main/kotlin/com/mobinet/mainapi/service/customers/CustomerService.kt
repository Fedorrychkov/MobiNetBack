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
//        return Resp.Error()
        return Resp.GetAllSuccess(findByDirectorId(directorId))
//        findByCreatorId(createByDirectorId)
//        return Resp.GetAllSuccess()
    }


    fun customersList(): List<Customers> = customersRepository.findAll()

    fun createNewCustomer(customers: Customers): Customers =
            customersRepository.save(customers)

    fun findByDirectorId(directorId: Long): List<Customers> {
        return customersRepository.findAllByDirectorId(directorId)
    }
//    fun findByDirectorId(directorId: Long): ResponseEntity<Customers> {
//        return customersRepository.findByDirectorId(directorId).map{ customer ->
//            println(customer)
//            return ResponseEntity.ok(customer)
//        }.getOrElse(directorId.toInt()){ ResponseEntity.noContent().build() }
//    }

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