package com.mobinet.mainapi

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MainapiApplication

fun main(args: Array<String>) {
    SpringApplication.run(MainapiApplication::class.java, *args)
}
