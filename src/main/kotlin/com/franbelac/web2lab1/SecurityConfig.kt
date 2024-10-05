package com.franbelac.web2lab1

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.invoke

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize("/api/tickets/total",permitAll)
                authorize("/api/tickets",authenticated)
                authorize("/api/tickets/**",hasRole("USER"))
            }
            oauth2ResourceServer {
                jwt {  }
            }
            oauth2Login {  }
        }
        return http.build()
    }

}