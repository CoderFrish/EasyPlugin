import extension.buildSettings.ServerType

plugins {
    java
    kotlin("jvm")
    id("build.plugin")
    id("io.papermc.paperweight.userdev") version "1.7.1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

val serverVersion: String by project

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle(serverVersion)
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    enabled = false
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.shadowJar {
    destinationDirectory.set(File(rootDir, "target"))
}

sourceSets {
    main {
        java {
            srcDirs("src/plugin")
        }

        kotlin {
            srcDirs("src/plugin")
        }

        resources {
            srcDirs("src/resources")
        }
    }
}

buildSettings {
    type = ServerType.PAPER
    version = serverVersion

    create("ExamplePlugin") {
        setMain("me.example.Example")
        setVersion(project.version as String)
        setAuthor("ME!!")
        setWebsite("https://github.com/PluginExample")
        setApiVersion("1.20")
    }
}
