import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.spring.kotlin)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependencyManagement)
}

dependencies {
    implementation(projects.tsBackendCommon)

    implementation(libs.kotlin.reflect)

    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.webMVC)
    implementation(libs.spring.boot.exposed)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.javaTime)

    runtimeOnly(libs.postgresqlDriver)
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
    jvmToolchain(21)
}