import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:1.2.5.RELEASE")
    }
}

plugins {
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
}

group = "ru.hse.group_project"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17


configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

//springBoot {
//    mainClass.set("ru.hse.group_project.nasvazi.NasvaziApplicationKt")
//}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:3.0.1")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.13")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    implementation ("com.twilio.sdk:twilio:9.2.4")
    // implementation ("org.springframework.boot:spring-boot-starter-parent:2.4.0")
    // implementation ("io.springfox:springfox-swagger-ui:2.6.1")
     implementation("io.swagger.codegen.v3:swagger-codegen-maven-plugin:3.0.16")
}

configurations.implementation {
    exclude(group = "org.springframework.data", module = "spring-data-commons")
    exclude(group = "org.hibernate.common", module = "hibernate-commons-annotations:5.1.0.Final")
}
tasks.bootJar {
    archiveClassifier.set("boot")
// manifest{
// attributes["Main-Class"]="ru.hse.group_project.nasvazi.NasvaziApplication"
// }
}
tasks.jar {
    archiveClassifier.set("")
}
tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}
tasks.withType<Wrapper> {
    gradleVersion = "8.0"
}

tasks.withType<Test> {
    useJUnitPlatform()
}
