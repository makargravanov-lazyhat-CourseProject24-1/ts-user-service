import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.spring.kotlin)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependencyManagement)
}

dependencies {
    implementation(rootProject.libs.spring.boot.starter)
    implementation(rootProject.libs.spring.webMVC)
    implementation(rootProject.libs.kotlin.reflect)
    implementation(rootProject.projects.tsBackendCommon)
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
    jvmToolchain(21)
}