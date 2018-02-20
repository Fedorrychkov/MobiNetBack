package com.mobinet.mainapi.model

import org.hibernate.validator.constraints.NotBlank
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
data class Customers (
        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        var id: Long = 0,
        var directorId: Long = 0,
        var firstName: String? = "",
        var lastName: String? = "",
        @get: NotBlank
        var email: String? = "",
        var phone: String? = "",
        var status: String? = "",
        var address: String? = "",
        var dateCreated: String? = "",
        var activeDate: String? = ""
)