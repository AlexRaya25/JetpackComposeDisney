plugins {
    id("java-library")
    kotlin("jvm")
}

dependencies{
    implementation(libs.kotlin.stdlib)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // Retrofit for network calls (if needed in domain)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // OkHttp for HTTP Client (if needed in domain)
    implementation(libs.okhttp.v493)

    // Gson for JSON parsing
    implementation(libs.gson)

    // Unit testing
    testImplementation(libs.junit)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}