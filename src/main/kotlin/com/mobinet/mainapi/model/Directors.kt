package com.mobinet.mainapi.model

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
data class Directors (
        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        var idDirectors: Long = 0,

        @get: NotBlank
        var username: String = "",


        var firstName: String? = "",

        var lastName: String? = "",

        @get: NotBlank
        var password: String? = "",

        @get: NotBlank
        var email: String? = "",

        var phone: String? = "",

        var token: String? = "",

        var birthDay: String? = "",

        var dateCreated: String? = "",

        var userConfirmed: Boolean? = null
)