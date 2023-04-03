dependencyResolutionManagement {
    repositories {
        mavenLocal()
        maven {
            url = uri("https://maven.aliyun.com/repository/public")
        }
        mavenCentral()
    }
}

val javaVersion = JavaVersion.current()
require(javaVersion == JavaVersion.VERSION_17) {
    "The project build must be executed with Java 17. Currently executing with Java ${javaVersion.majorVersion}."
}

rootProject.name = "gradle-quickstart"
