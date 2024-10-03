plugins {
    id("das-architectures.common-conventions")
}

dependencies {
    implementation(libs.azure.identity)
    implementation(libs.azure.messaging.servicebus)
}