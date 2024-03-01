package com.github.player13.ates.auth.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain


@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Configuration(proxyBeanMethods = false)
class SecurityConfig {

    @Bean
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests { authorize -> authorize.anyRequest().authenticated() }

        /* val jwtAuthenticationConverter = JwtAuthenticationConverter()
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter { jwt ->
            (jwt.claims["authority"] as? List<*>)
                ?.mapNotNull { role -> (role as? String)?.let { SimpleGrantedAuthority(it) } }
                ?: listOf()
        }
        http.oauth2ResourceServer { resourceServerConfigurer ->
            resourceServerConfigurer.jwt { jwtConfigurer ->
                jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)
            }
        } */

        http.formLogin(Customizer.withDefaults())
        http.csrf { it.disable() }
        http.cors { it.disable() }
        // http.cors { it.configurationSource(corsConfigurationSource()) }
        return http.build()
        // return http.formLogin { it.defaultSuccessUrl("/oauth2/authorize", true) }.build()
    }

    /* @Bean
    fun users(): UserDetailsService {
        val user: UserDetails = User.builder()
            .username("admin")
            .password("{noop}password")
            .roles("ADMIN111")
            .build()
        return InMemoryUserDetailsManager(user)
    } */


    /* @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()

        // Allow anyone and anything access. Probably ok for Swagger spec
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("http://localhost:8081")
        config.addAllowedHeader("*")
        config.addAllowedMethod("POST")
        config.addAllowedMethod("OPTIONS")
        source.registerCorsConfiguration("/oauth2/token", config)
        // source.registerCorsConfiguration("/swagger-ui/index.html", config)
        return CorsFilter(source)
    } */

    // @Bean
    // fun corsConfigurationSource(): CorsConfigurationSource {
    //     val configuration = CorsConfiguration()
    //     configuration.allowedOrigins = listOf("*")
    //     configuration.addAllowedHeader("*")
    //     configuration.addAllowedMethod("*")
    //     configuration.allowCredentials = false
    //     val source = UrlBasedCorsConfigurationSource()
    //     source.registerCorsConfiguration("/**", configuration)
    //     return source
    // }
}