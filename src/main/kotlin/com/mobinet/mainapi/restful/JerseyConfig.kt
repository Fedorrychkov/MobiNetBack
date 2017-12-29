package com.mobinet.mainapi.restful

import org.glassfish.jersey.server.ResourceConfig
import org.springframework.stereotype.Component

@Component
open class JerseyConfig: ResourceConfig {
    constructor() {
        packages(JerseyConfig::class.java.`package`.name)
    }
}