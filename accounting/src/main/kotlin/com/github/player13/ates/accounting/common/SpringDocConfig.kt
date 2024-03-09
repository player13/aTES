package com.github.player13.ates.accounting.common

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.OAuthFlow
import io.swagger.v3.oas.annotations.security.OAuthFlows
import io.swagger.v3.oas.annotations.security.OAuthScope
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.context.annotation.Configuration

@SecurityScheme(
    name = "security_auth",
    type = SecuritySchemeType.OAUTH2,
    flows = OAuthFlows(
        authorizationCode = OAuthFlow(
            authorizationUrl = "\${springdoc.swagger-ui.oauth.authorization-url}",
            tokenUrl = "\${springdoc.swagger-ui.oauth.token-url}",
            refreshUrl = "\${springdoc.swagger-ui.oauth.refresh-url}",
            // scopes = [OAuthScope(name = "qweqwe", description = "pffff")], // todo: scopes
        ),
    ),
)
@Configuration
class SpringDocConfig