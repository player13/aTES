import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
	id("org.springframework.boot") version "3.2.2" apply false
	id("io.spring.dependency-management") version "1.1.4" apply false
	id("com.github.davidmc24.gradle.plugin.avro") version "1.9.1" apply false
	id("com.github.imflog.kafka-schema-registry-gradle-plugin") version "1.13.0" apply false
	kotlin("jvm") version "1.9.22" apply false
	kotlin("plugin.spring") version "1.9.22" apply false
	kotlin("plugin.jpa") version "1.9.22" apply false
}

val avroVersion: String by project
val springdocVersion: String by project
val kafkaAvroVersion: String by project

subprojects {
	group = "com.github.player13.ates"
	version = "0.0.1-SNAPSHOT"

	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.jetbrains.kotlin.jvm")

	repositories {
		mavenCentral()
		maven { url = uri("https://packages.confluent.io/maven") }
	}

	configure<DependencyManagementExtension> {
		imports {
			mavenBom(SpringBootPlugin.BOM_COORDINATES)
			mavenBom("org.springdoc:springdoc-openapi:$springdocVersion")
			// mavenBom("...")
		}
		dependencies {
			dependency("org.apache.avro:avro:$avroVersion")
			dependency("io.confluent:kafka-avro-serializer:$kafkaAvroVersion")
			// dependency("...")
		}
	}

	tasks.withType<JavaCompile> {
		sourceCompatibility = "17"
		targetCompatibility = "17"
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs += "-Xjsr305=strict"
			jvmTarget = "17"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

