// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.sonarqube") version "4.4.1.3373"
}

sonar {
    properties {
        property("sonar.projectKey", "EmKaaaM_MindScribe")
        property("sonar.organization", "team13")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}