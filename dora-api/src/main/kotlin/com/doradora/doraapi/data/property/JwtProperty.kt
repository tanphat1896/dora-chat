package com.doradora.doraapi.data.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "jwt")
class JwtProperty {
    var expiry: Long = 0
    var secret: String = ""
}