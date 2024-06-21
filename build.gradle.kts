plugins {
	java
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	id("org.sonarqube") version "4.4.1.3373"
}

group = "com.github.renas"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

sonar {
	properties {
		property("sonar.projectKey", "renasustek_RecepieFinder")
		property("sonar.organization", "renasustek")
		property("sonar.host.url", "https://sonarcloud.io")
	}
}


dependencies {

	implementation(platform("org.springframework.boot:spring-boot-dependencies:3.2.2"))
	implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("mysql:mysql-connector-java:8.0.33")
	implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")
	implementation("org.hibernate:hibernate-validator:8.0.1.Final")

	testImplementation("org.junit.jupiter:junit-jupiter:5.4.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
