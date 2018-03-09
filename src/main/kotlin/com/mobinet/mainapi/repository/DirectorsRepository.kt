package com.mobinet.mainapi.repository

import com.mobinet.mainapi.model.Directors
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DirectorsRepository: JpaRepository<Directors, Long> {
    fun findByUsername(username: String): Iterable<Directors>
    fun findByPassword(password: String?): Iterable<Directors>
    fun findByOAuthToken(oAuthToken: String?): Iterable<Directors>
    fun findByEmail(email: String?): Iterable<Directors>
    fun findById(id: Long): Iterable<Directors>
}