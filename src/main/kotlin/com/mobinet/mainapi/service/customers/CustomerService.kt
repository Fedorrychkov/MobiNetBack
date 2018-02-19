package com.mobinet.mainapi.service.customers

import com.mobinet.mainapi.model.Customers
import com.mobinet.mainapi.repository.CustomersRepository
import org.springframework.stereotype.Component

@Component
class CustomerService(private val customersRepository: CustomersRepository) {
    interface Resp {
        class Success(val msg: String, val requestBody: Customers?): Resp
        class Error(): Resp
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

    fun createNewCustomer(customers: Customers): Customers =
            customersRepository.save(customers)
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