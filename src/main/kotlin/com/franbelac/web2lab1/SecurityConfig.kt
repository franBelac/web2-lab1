package com.franbelac.web2lab1

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    @Order(2)
    fun loginFilter(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize(method = HttpMethod.GET, pattern = "/api/tickets/overview/**",authenticated)
                authorize(method = HttpMethod.GET, pattern = "/api/tickets/total",permitAll)
            }
            oauth2Login {  }
        }

        return http.build()
    }

    @Bean
    @Order(1)
    fun resourceServerFilter(http: HttpSecurity) : SecurityFilterChain {
        http {
            securityMatcher(RequestHeaderRequestMatcher("Authorization"))
            authorizeHttpRequests {
                authorize(method = HttpMethod.POST, pattern = "/api/tickets",authenticated)
            }

            oauth2ResourceServer {
                jwt {  }
            }
        }
        return http.build()
    }
}