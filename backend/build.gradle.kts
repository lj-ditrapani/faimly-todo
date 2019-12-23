import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

extra["log4jVersion"] = "2.12.1"
extra["vertxVersion"] = "3.8.3"

plugins {
    kotlin("jvm").version("1.3.61")
    application
    jacoco
    id("org.jlleitschuh.gradle.ktlint").version("9.1.1")
}

repositories {
    jcenter()
}

dependencies {
    val log4jVersion = rootProject.ext["log4jVersion"]
    val vertxVersion = rootProject.ext["vertxVersion"]

    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("io.vertx:vertx-lang-kotlin:$vertxVersion")
    implementation("io.vertx:vertx-lang-kotlin-coroutines:$vertxVersion")
    implementation("io.vertx:vertx-web:$vertxVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

application {
    mainClassName = "info.ditrapani.todo.AppKt"
}

ktlint {
    version.set("0.35.0")
    enableExperimentalRules.set(true)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.test {
    testLogging {
        events("passed", "started", "failed", "skipped")
    }
    useJUnitPlatform()
}
