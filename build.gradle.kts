import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	war
	java
	id("org.springframework.boot") version "3.1.4"
	id("io.spring.dependency-management") version "1.1.3"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
	id("se.thinkcode.cucumber-runner") version "0.0.8"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenLocal()
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("org.springframework.boot:spring-boot-starter-integration")
	implementation("org.springframework.integration:spring-integration-feed")
	implementation("org.springframework.integration:spring-integration-file")
	implementation("org.springframework.integration:spring-integration-kafka")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("jakarta.validation:jakarta.validation-api:3.0.2")
	implementation("org.apache.kafka:kafka-clients:3.6.1")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("io.projectreactor.kafka:reactor-kafka:1.3.22")

	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.integration:spring-integration-test")
	testImplementation(platform("org.junit:junit-bom"))
	testImplementation("org.junit.platform:junit-platform-suite")
	testImplementation("org.junit.jupiter:junit-jupiter")
	testImplementation("io.cucumber:cucumber-java:7.14.0")
	testImplementation("io.cucumber:cucumber-junit:7.14.0")
	testImplementation("net.serenity-bdd:serenity-core")
	testImplementation("net.serenity-bdd:serenity-junit5:3.5.1")
	testImplementation("net.serenity-bdd:serenity-screenplay:3.5.1")
	testImplementation("net.serenity-bdd:serenity-ensure:3.5.1")
	testImplementation("net.serenity-bdd:serenity-screenplay-webdriver:3.5.1")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
	testImplementation("org.assertj:assertj-core")
	testImplementation("org.junit.vintage:junit-vintage-engine:5.10.0")
	testImplementation("io.cucumber:cucumber-java8:7.14.0")
	testImplementation("io.rest-assured:rest-assured:5.3.2")
	testImplementation("io.rest-assured:kotlin-extensions:5.3.2")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.7.1")
	testImplementation("org.junit.platform:junit-platform-launcher:1.5.2")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
	testLogging.showStandardStreams = true
}