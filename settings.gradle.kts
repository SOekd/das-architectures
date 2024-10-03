rootProject.name = "das-architectures"

pluginManagement {
    includeBuild("build-logic")

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

sequenceOf(
    "event",
).forEach {
    include("das-architectures-$it")
    project(":das-architectures-$it").projectDir = file(it)
}

sequenceOf(
    "kernel",
    "plugin",
    "common"
).forEach {
    include("das-architectures-$it")
    project(":das-architectures-$it").projectDir = file("microkernel/$it")
}