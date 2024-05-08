import com.vanniktech.maven.publish.SonatypeHost

plugins {
    java
    signing
    id("com.vanniktech.maven.publish") version "0.28.0"
}

group = "io.badgod"
version = "0.0.2"


repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.httpcomponents.core5:httpcore5:5.2.4")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("org.hamcrest:hamcrest-library:2.2")
    testImplementation("org.testcontainers:testcontainers:1.19.7")
    testImplementation("org.testcontainers:junit-jupiter:1.19.3")
    testImplementation("org.slf4j:slf4j-simple:2.0.11")
    testImplementation("com.google.code.gson:gson:2.10.1")

}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    testLogging.events("failed")
    testLogging.showExceptions = true
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()


    coordinates("io.badgod", "jayreq", "0.0.2")

    pom {
        name.set("JayReq")

        description.set("A batteries included Java HTTP client")
        inceptionYear.set("2024")
        url.set("https://github.com/robpelger/jayreq")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("robpelger")
                name.set("Robert Pelger")
                url.set("https://github.com/robpelger/")
            }
        }
        scm {
            url.set("https://github.com/robpelger/jayreq.git")
            connection.set("scm:git:git://github.com/robpelger/jayreq.git")
            developerConnection.set("scm:git:ssh://git@github.com/robpelger/jayreq.git")
        }
    }
}


