plugins {
    java
    `maven-publish`
}

group = "io.badgod"
version = "0.0.2"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.apache.httpcomponents.core5:httpcore5:5.2.4")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("org.hamcrest:hamcrest-library:2.2")
    testImplementation("org.testcontainers:testcontainers:1.19.3")
    testImplementation("org.testcontainers:junit-jupiter:1.19.3")
    testImplementation("org.slf4j:slf4j-simple:2.0.11")

}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    testLogging.events("failed");
    testLogging.showExceptions = true
}

group = "io.badgod"
version = "0.0.2"

java {
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "jayreq"
            from(components["java"])

            pom {
                name = "JayReq"
                description = "A batteries included Java HTTP client"
                url = "http://www.example.com/library"
                packaging = "jar"
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "robpelger"
                        name = "Robert Pelger"
                        email = "robert.pelger@hey.com"
                    }
                }
                scm {
                    connection = "scm:git:https://github.com/robpelger/jayreq.git"
                    developerConnection = "scm:git:git@github.com:robpelger/jayreq.git"
                    url = "https://github.com/robpelger/jayreq"
                }
            }
        }
    }
}


