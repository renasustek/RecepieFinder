plugins {
	java
	jacoco
	id("com.diffplug.spotless") version "7.0.0.BETA1"
	id("org.sonarqube") version "4.4.1.3373"
	id("org.springframework.boot") version "3.3.1"
}

val springDependencies = "3.3.1"
val mysqlConnector = "8.0.33"
val hibernate = "8.0.1.Final"
val junit = "5.10.3"
val palantirJavaFormat = "2.47.0"

val assertJ = "3.26.0"

group = "com.github.renas"

version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}
dependencies {

	implementation(platform("org.springframework.boot:spring-boot-dependencies:$springDependencies"))

	implementation("mysql:mysql-connector-java:$mysqlConnector")
	implementation("org.hibernate.validator:hibernate-validator:$hibernate")
	implementation("org.hibernate:hibernate-validator:$hibernate")
	implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")

	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.assertj:assertj-core:$assertJ")
	testImplementation("org.junit.jupiter:junit-jupiter:$junit")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}


tasks.test {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
	reports {
		xml.required = true
	}
	dependsOn(tasks.test)
}
sonar {
	properties {
		property("sonar.projectKey", "renasustek_RecepieFinder")
		property("sonar.organization", "renasustek")
		property("sonar.host.url", "https://sonarcloud.io")
	}
}

tasks.sonar {
	dependsOn(tasks.check)
}
spotless {
	ratchetFrom("origin/main")
	java {
		toggleOffOn()
		palantirJavaFormat(palantirJavaFormat).formatJavadoc(true)
		removeUnusedImports()
		trimTrailingWhitespace()
		endWithNewline()
	}
}

