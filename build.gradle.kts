plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("com.h2database:h2:2.3.232")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.postgresql:postgresql:42.7.4")
    implementation ("com.h2database:h2:2.2.224")
    implementation("org.flywaydb:flyway-core:10.18.2")
    compileOnly("org.projectlombok:lombok:1.18.34")
    implementation("com.zaxxer:HikariCP:6.0.0")
}

tasks.test {
    useJUnitPlatform()
}