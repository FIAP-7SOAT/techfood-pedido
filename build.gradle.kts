import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	val kotlinVersion = "1.9.24"

	kotlin("jvm") version kotlinVersion
	kotlin("plugin.noarg") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
	kotlin("plugin.jpa") version kotlinVersion

	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.flywaydb.flyway") version "10.13.0"
	id("jacoco")
}

group = "br.com.fiap"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

val kotlinLoggingVersion = "3.0.5"
val mockkVersion = "1.13.11"
val springCloudVersion = "2023.0.1"
val springMockk = "4.0.2"

dependencies {
	modules {
		module("org.springframework.boot:spring-boot-starter-tomcat") {
			replacedBy("org.springframework.boot:spring-boot-starter-undertow", "Use Undertow instead of tomcat")
		}
	}
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-undertow")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.cloud:spring-cloud-starter")
	implementation("org.springframework.cloud:spring-cloud-starter-config")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")

	implementation("org.postgresql:postgresql:42.3.3")

	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

	testImplementation("com.h2database:h2")
	testImplementation("com.ninja-squad:springmockk:$springMockk")
	testImplementation("com.tngtech.archunit:archunit-junit5:1.3.0")
	testImplementation("io.mockk:mockk:$mockkVersion")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock")

	testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
	testImplementation("org.mockito:mockito-core:4.0.0")
	testImplementation("org.jetbrains.kotlin:kotlin-test:1.7.0")  // Kotlin test dependencies



}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
	}
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

jacoco {
	toolVersion = "0.8.8" // Specify JaCoCo version
}

tasks.jacocoTestReport {
	dependsOn("test") // Make sure that tests are executed before generating the report

	reports {
		xml.required.set(true) // Generate XML report
		html.required.set(true) // Generate HTML report
	}

	// Direct configuration of classDirectories without afterEvaluate
	classDirectories.setFrom(
		fileTree("$buildDir/classes/kotlin/main") {
			exclude(
				"**/app/**", // Excluindo o pacote 'app'
				"**/dto/**", // DTOs
				"**/mapper/**", // Mapeadores
				"**/configuration/**", // Configurações
				"**/generated/**", // Código gerado
				"**/web/handler/**"      // Excluir o pacote web.handler
			)
		}
	)
}

tasks.jacocoTestCoverageVerification {
	dependsOn(tasks.test)

	violationRules {
		rule {
			limit {
				minimum = 0.50.toBigDecimal() // 80% de cobertura mínima
			}
		}
	}
}