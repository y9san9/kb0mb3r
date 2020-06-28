plugins {
    java
    kotlin("jvm") version "1.3.72"
}

group = "com.y9san9.b0mb3r"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.kittinunf.fuel:fuel:2.2.0")
    implementation("com.google.code.gson:gson:2.8.6")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}