package com.mobinet.mainapi.repository

import com.mobinet.mainapi.model.Orders
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrdersRepository : JpaRepository<Orders, Long> {
    fun findById(id: Long): Iterable<Orders>
    fun findByDirectorId(id: Long, directorId: Long): Iterable<Orders>
    fun findAllByDirectorId(directorId: Long): List<Orders>
}