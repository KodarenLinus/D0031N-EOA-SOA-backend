plugins {
	id("org.springframework.boot") version "3.5.7"
	id("io.spring.dependency-management") version "1.1.7"
	id("java")
}

repositories { mavenCentral() }

dependencies {
	// REST + validering + DataSource
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")

	// JDBI (måste ha båda)
	implementation("org.jdbi:jdbi3-core:3.49.6")
	implementation("org.jdbi:jdbi3-sqlobject:3.49.6")

	// DB-drivrutiner
	runtimeOnly("com.h2database:h2")                // för lokal dev
	runtimeOnly("org.postgresql:postgresql")       // om du kör Postgres

	// Migrationer (valfritt men bra)
	implementation("org.flywaydb:flyway-core")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}
