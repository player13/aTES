package com.github.player13.ates.auth.security

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.UUID
import java.util.stream.Collectors
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Role
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint


@Configuration
// @EnableAuthorizationServer
class AuthServerConfig {

    // private val authorizationServerProperties: AuthorizationServerProperties? = null

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun authServerSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http)
        http.exceptionHandling { exceptions: ExceptionHandlingConfigurer<HttpSecurity> ->
            exceptions.authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login"))
        }
        // http.sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        http.cors { it.disable() }
        http.csrf { it.disable() }
        return http.build()
    }

    @Bean
    fun registeredClientRepository(): RegisteredClientRepository {
        return InMemoryRegisteredClientRepository(
            RegisteredClient.withId("fbe02ba8-18c0-485e-a7c8-fa2e39abc5c6")
                .clientName("Task Client")
                .clientId("task-client")
                .clientSecret("{noop}task-client") // todo: move to params
                .redirectUri("http://localhost:8081/swagger-ui/oauth2-redirect.html")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .build(),
            RegisteredClient.withId("39238d49-9342-4c5c-9016-f829fc290812")
                .clientName("Accounting Client")
                .clientId("accounting-client")
                .clientSecret("{noop}accounting-client")
                .redirectUri("http://localhost:8082/swagger-ui/oauth2-redirect.html")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .build(),
            RegisteredClient.withId("6ef85733-369b-47b7-b56d-53d7ff315560")
                .clientName("Analytics Client")
                .clientId("analytics-client")
                .clientSecret("{noop}analytics-client")
                .redirectUri("http://localhost:8083/swagger-ui/oauth2-redirect.html")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .build(),
        )
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    fun generateRsaKey(): KeyPair =
        try {
            val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
            keyPairGenerator.initialize(2048)
            keyPairGenerator.generateKeyPair()
        } catch (ex: Exception) {
            throw IllegalStateException(ex)
        }

    @Bean
    fun jwkSource(keyPair: KeyPair): JWKSource<SecurityContext> {
        val publicKey = keyPair.public as RSAPublicKey
        val privateKey = keyPair.private as RSAPrivateKey
        val rsaKey = RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyID(UUID.randomUUID().toString())
            .build()
        val jwkSet = JWKSet(rsaKey)
        return ImmutableJWKSet(jwkSet)
    }

    @Bean
    fun jwtDecoder(keyPair: KeyPair): JwtDecoder =
        NimbusJwtDecoder.withPublicKey(keyPair.public as RSAPublicKey)
            .build()


    @Bean
    fun authorizationServerSettings(): AuthorizationServerSettings {
        return AuthorizationServerSettings.builder()
            .issuer("http://localhost:8080") // todo: move to params
            .build()
    }

    @Bean
    fun jwtCustomizer(): OAuth2TokenCustomizer<JwtEncodingContext> {
        return OAuth2TokenCustomizer<JwtEncodingContext> { context: JwtEncodingContext ->
            if (context.tokenType === OAuth2TokenType.ACCESS_TOKEN) {
                val principal: Authentication = context.getPrincipal()
                val authorities: Set<String> = principal.authorities
                    .map { obj: GrantedAuthority -> obj.authority }
                    .toSet()
                context.claims.claim("authority", authorities)
            }
        }
    }

    /* @Bean
    fun providerSettings(): ProviderSettings {
        return ProviderSettings.builder()
            .issuer("https://authorization-server:8443")
            .build()
    } */
}