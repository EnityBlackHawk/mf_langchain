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
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("dev.langchain4j:langchain4j:0.27.1")
    implementation("dev.langchain4j:langchain4j-open-ai:0.27.1")
    implementation("dev.langchain4j:langchain4j-local-ai:0.27.1")
    implementation("dev.langchain4j:langchain4j-hugging-face:0.27.1")
    implementation("dev.langchain4j:langchain4j-vertex-ai-gemini:0.27.1")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
