rootProject.name = "das-architectures-build-logic"


dependencyResolutionManagement {
    repositories {
        mavenCentral()

    }

    versionCatalogs {
        register("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}