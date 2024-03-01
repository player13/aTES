plugins {
	id("org.springframework.boot")
	kotlin("plugin.spring")
	kotlin("plugin.jpa")
}

dependencies {
	// kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// spring
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.security:spring-security-oauth2-resource-server")
	implementation("org.springframework.security:spring-security-oauth2-jose")
	implementation("org.springframework.kafka:spring-kafka")

	// swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui")

	// jackson
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// database
	implementation("org.flywaydb:flyway-core")
	runtimeOnly("org.postgresql:postgresql")

	// test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}
