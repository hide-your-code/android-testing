object GradleVersion {
    const val gradleVersion = "4.2.1"
    const val kotlinVersion = "1.5.0"
    const val navigationVersion = "2.3.5"
    const val hiltVersion = "2.35"
    const val jacocoVersion = "0.8.7"
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.0")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.35")
        classpath("org.jacoco:org.jacoco.core:0.8.7")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
