plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    jacoco
}

// Define versions in a single place
object Version {

    // Sdk and tools
    // Support library and architecture components support minSdk 14 and above.
    const val minSdkVersion = 21
    const val targetSdkVersion = 30
    const val compileSdkVersion = 30

    // App dependencies
    const val androidXVersion = "1.0.0"
    const val androidXTestCoreVersion = "1.2.0"
    const val androidXTestExtKotlinRunnerVersion = "1.1.2"
    const val androidXTestRulesVersion = "1.2.0-beta01"
    const val androidXAnnotations = "1.2.0"
    const val androidXLegacySupport = "1.0.0"
    const val appCompatVersion = "1.2.0"
    const val archLifecycleVersion = "2.3.1"
    const val archLifecycleExtVersion = "2.2.0"
    const val archTestingVersion = "2.0.0"
    const val cardVersion = "1.0.0"
    const val coroutinesVersion = "1.4.3"
    const val dexMakerVersion = "2.12.1"
    const val espressoVersion = "3.2.0-beta01"
    const val fragmentVersion = "1.3.3"
    const val fragmentKtxVersion = "1.3.3"
    const val hamcrestVersion = "1.3"
    const val hiltVersion = "2.35"
    const val junitVersion = "4.13.2"
    const val kotlinVersion = "1.5.0"
    const val materialVersion = "1.3.0"
    const val mockitoVersion = "3.9.0"
    const val mockKVersion = "1.11.0"
    const val navigationVersion = "2.3.5"
    const val powerMockVersion = "2.0.9"
    const val recyclerViewVersion = "1.2.0"
    const val robolectricVersion = "4.3.1"
    const val roomVersion = "2.3.0"
    const val rulesVersion = "1.0.1"
    const val timberVersion = "4.7.1"
    const val truthVersion = "0.44"
}

android {
    compileSdkVersion(Version.compileSdkVersion)

    defaultConfig {
        applicationId = "minhdtm.example.unittest.todoapp"
        minSdkVersion(Version.minSdkVersion)
        targetSdkVersion(Version.targetSdkVersion)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            isTestCoverageEnabled = true
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    testOptions {
        execution = "ANDROID_TEST_ORCHESTRATOR"
        animationsDisabled = true
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    buildFeatures {
        dataBinding = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // App dependencies
    implementation("androidx.appcompat:appcompat:${Version.appCompatVersion}")
    implementation("androidx.cardview:cardview:${Version.cardVersion}")
    implementation("com.google.android.material:material:${Version.materialVersion}")
    implementation("androidx.recyclerview:recyclerview:${Version.recyclerViewVersion}")
    implementation("androidx.annotation:annotation:${Version.androidXAnnotations}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutinesVersion}")
    implementation("com.jakewharton.timber:timber:${Version.timberVersion}")
    implementation("androidx.legacy:legacy-support-v4:${Version.androidXLegacySupport}")
    implementation("androidx.room:room-runtime:${Version.roomVersion}")
    kapt("androidx.room:room-compiler:${Version.roomVersion}")

    // Architecture Components
    implementation("androidx.room:room-runtime:${Version.roomVersion}")
    kapt("androidx.room:room-compiler:${Version.roomVersion}")
    implementation("androidx.room:room-ktx:${Version.roomVersion}")
    implementation("androidx.lifecycle:lifecycle-extensions:${Version.archLifecycleExtVersion}")
    kapt("androidx.lifecycle:lifecycle-compiler:${Version.archLifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.archLifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Version.archLifecycleVersion}")
    implementation("androidx.navigation:navigation-fragment-ktx:${Version.navigationVersion}")
    implementation("androidx.navigation:navigation-ui-ktx:${Version.navigationVersion}")

    // Dependencies for local unit tests
    testImplementation("junit:junit:${Version.junitVersion}")
    testImplementation("org.powermock:powermock-module-junit4:${Version.powerMockVersion}")
    testImplementation("org.powermock:powermock-api-mockito2:${Version.powerMockVersion}")
    testImplementation("io.mockk:mockk:${Version.mockKVersion}")
    testImplementation("org.mockito:mockito-core:${Version.mockitoVersion}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.9")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.0")

    // AndroidX Test - Instrumented testing
    androidTestImplementation("androidx.test.ext:junit:${Version.androidXTestExtKotlinRunnerVersion}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Version.espressoVersion}")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Version.kotlinVersion}")
    implementation("androidx.fragment:fragment-ktx:${Version.fragmentKtxVersion}")

    // Hilt
    implementation("com.google.dagger:hilt-android:${Version.hiltVersion}")
    kapt("com.google.dagger:hilt-compiler:${Version.hiltVersion}")
}

jacoco {
    toolVersion = "0.8.7"
}

project.afterEvaluate {
    // Grab all build types and product flavors
    val buildTypeNames: List<String> = android.buildTypes.map { it.name }
    val productFlavorNames: List<String> = android.applicationVariants
        .map { it.flavorName }
        .sorted()

    productFlavorNames.forEachIndexed { index, productFlavorName ->
        val buildTypeName = buildTypeNames[index.rem(buildTypeNames.size)]
        val sourceName: String
        val sourcePath: String
        if (productFlavorName.isEmpty()) {
            sourcePath = buildTypeName
            sourceName = buildTypeName
        } else {
            sourcePath = "${productFlavorName}/${buildTypeName}"
            sourceName = "${productFlavorName}${buildTypeName.capitalize()}"
        }
        val testTaskName = "test${sourceName.capitalize()}UnitTest"
        // Create coverage task of form 'testFlavorTypeCoverage' depending on 'testFlavorTypeUnitTest'
        task<JacocoReport>("${testTaskName}Coverage") {
            //where store all test to run follow second way above
            group = "coverage"
            description =
                "Generate Jacoco coverage reports on the ${sourceName.capitalize()} build."

            reports {
                html.isEnabled = true
                xml.isEnabled = true
                xml.destination =
                    file("${project.rootDir}/.sun-ci-reports/coverage/coverage.xml")
                html.destination = file("${project.rootDir}/.sun-ci-reports/coverage/")
            }

            val excludeFiles = arrayListOf(
                "**/R.class", "**/R$*.class", "**/BuildConfig.*", "**/Manifest*.*",
                "**/*Test*.*", "android/**/*.*",
                "**/*_MembersInjector.class",
                "**/Dagger*Component.class",
                "**/Dagger*Component\$Builder.class",
                "**/*_*Factory.class",
                "**/*ComponentImpl.class",
                "**/*SubComponentBuilder.class",
                "**/*Creator.class",
                "**/*Application*.*",
                "**/*Activity*.*",
                "**/*Fragment*.*",
                "**/*Adapter*.*",
                "**/*Dialog*.*",
                "**/*Args*.*",
                "**/*Companion*.*",
                "**/*Kt*.*",
                "**/jp/co/baseball/di/**/*.*",
                "**/jp/co/baseball/ui/navigation/**/*.*",
                "**/jp/co/baseball/ui/widgets/**/*.*"
            )

            //Explain to Jacoco where are you .class file java and kotlin
            classDirectories.setFrom(
                fileTree("${project.buildDir}/intermediates/classes/${sourcePath}").exclude(
                    excludeFiles
                ),
                fileTree("${project.buildDir}/tmp/kotlin-classes/${sourceName}").exclude(
                    excludeFiles
                )
            )
            val coverageSourceDirs = arrayListOf(
                "src/main/java",
                "src/$productFlavorName/java",
                "src/$buildTypeName/java"
            )

            additionalSourceDirs.setFrom(files(coverageSourceDirs))

            //Explain to Jacoco where is your source code
            sourceDirectories.setFrom(files(coverageSourceDirs))

            //execute file .exec to generate data report
            executionData.setFrom(files("${project.buildDir}/jacoco/${testTaskName}.exec"))

            reports {
                xml.isEnabled = true
                html.isEnabled = true
            }
            dependsOn(testTaskName)
        }
    }
}

tasks.register("copyTesResultReports", Copy::class.java) {
    android.applicationVariants.all {
        val variantName = this.name
        val testTaskName = "test${variantName.capitalize()}UnitTest"
        from("${project.buildDir}/reports/tests/$testTaskName")
        into("${project.rootDir}/.reports/summary")
    }
}
