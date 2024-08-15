plugins {
    `java-gradle-plugin`
}

gradlePlugin {
    plugins {
        create("plugin") {
            id = "build.plugin"
            implementationClass = "BuildPlugin"
        }
    }
}

repositories {
    mavenCentral()
    maven("https://repo1.maven.org/maven2")
}

dependencies {
    implementation("com.esotericsoftware.yamlbeans:yamlbeans:1.14")
}

tasks.test {
    enabled = false
}

sourceSets {
    main {
        java {
            srcDirs("src")
        }

        resources {
            srcDirs("src")
        }
    }
}