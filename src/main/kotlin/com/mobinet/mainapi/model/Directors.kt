package com.mobinet.mainapi.model

import javax.persistence.*

@Entity
@Table(name="directors")
internal data class Directors(@Id val id: Long? = null, val firstname: String, val lastname: String) {
}