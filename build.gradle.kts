plugins {
    java
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "org.mf"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("dev.langchain4j:langchain4j:0.23.0")
    implementation("dev.langchain4j:langchain4j-open-ai:0.27.1")
    implementation("dev.langchain4j:langchain4j-local-ai:0.27.1")
    implementation("dev.langchain4j:langchain4j-hugging-face:0.27.1")


}

tasks.withType<Test> {
    useJUnitPlatform()
}
