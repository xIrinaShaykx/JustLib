import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions { jvmTarget = "1.8" }

val outputName = "DopeLibAPI"
val outputDir = "OUTPUT"

group = "com.github.xIrinaShaykx"
version = "1.0.0-SNAPSHOT"



plugins {
    maven
    java
    kotlin("jvm") version "1.5.21"
}

tasks.jar {
    //Output name
    //duplicatesStrategy = org.gradle.api.file.DuplicatesStrategy.EXCLUDE
    archiveFileName.set("$outputName.jar")
    destinationDirectory.set(file("../OUTPUT"))
    //Shade dependecies

    configurations.compileClasspath.get().filter {
        it.name.startsWith("SpigotLibrary") || it.name.startsWith("KotlinLibrary") || it.name.startsWith("kotlin") || it.name.startsWith("h2")
    }.forEach { from(zipTree(it.absolutePath)) }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    maven("https://repo.kinqdos.de/artifactory/kinqdos-repo")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.kinqdos", "spigot", "1.17")
    implementation(kotlin("stdlib-jdk8"))
}