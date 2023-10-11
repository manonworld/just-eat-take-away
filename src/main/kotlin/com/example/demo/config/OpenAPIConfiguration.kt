package com.example.demo.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.context.annotation.Configuration
import io.swagger.v3.oas.annotations.info.Info


@Configuration
@SecurityScheme(name = "basicAuth", type = SecuritySchemeType.HTTP, scheme = "basic")
@OpenAPIDefinition(
    info = Info(title = "Sample API", version = "v1"),
    security = [SecurityRequirement(name = "basicAuth")]
)
internal class OpenAPIConfiguration