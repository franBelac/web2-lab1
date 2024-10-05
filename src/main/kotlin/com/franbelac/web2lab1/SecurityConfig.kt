package com.franbelac.web2lab1

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.invoke

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    @Order(1)
    fun resourceServerFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize("/api/tickets/total",permitAll)
                authorize("/api/tickets",authenticated)
            }
            oauth2ResourceServer {
                jwt {  }
            }
        }
        return http.build()
    }

    @Bean
    @Order(2)
    fun loginFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize("/api/tickets/**",authenticated)
            }
            oauth2Login {  }
        }

        return http.build()
    }

}