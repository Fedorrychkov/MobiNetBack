package com.mobinet.mainapi.repository

import com.mobinet.mainapi.model.Customers
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomersRepository: JpaRepository<Customers, Long> {
    fun findByPhone(phone: String?): Iterable<Customers>
    fun findByEmail(email: String?): Iterable<Customers>
    fun findById(id: Long): Iterable<Customers>
    fun findByDirectorId(id: Long, directorId: Long): Iterable<Customers>
    fun findAllByDirectorId(directorId: Long): List<Customers>
}