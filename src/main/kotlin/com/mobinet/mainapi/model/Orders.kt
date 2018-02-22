package com.mobinet.mainapi.model

import javax.persistence.*

@Entity
data class Orders (
        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        var id: Long = 0,
        var status: String? = "",
        var address: String? = "",
        var directorId: Long = 0,
        val customerId: Long = 0,
        var dateCreated: String? = "",
        var activeDate: String? = ""
)