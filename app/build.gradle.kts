plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    kotlin("kapt")
}

android {
    compileSdkVersion(AppConfig.compileSdk)
    buildToolsVersion(AppConfig.buildToolsVersion)

    defaultConfig {
        applicationId = "dev.dmanluc.openbankmobiletest"
        minSdkVersion(AppConfig.minSdk)
        targetSdkVersion(AppConfig.targetSdk)
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner(AppConfig.androidTestInstrumentation)
    }

    buildFeatures {
        viewBinding = true
    }

    sourceSets.getByName("main") {
        java.srcDir("src/main/java")
        java.srcDir("src/main/kotlin")
    }

    sourceSets.getByName("test") {
        java.srcDir("src/test/java")
        java.srcDir("src/test/kotlin")
    }

    sourceSets.getByName("androidTest") {
        java.srcDir("src/androidTest/java")
        java.srcDir("src/androidTest/kotlin")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    val marvelBaseUrl = (project.property("MARVEL_BASE_URL") as? String).orEmpty()
    val marvelApiKey = (project.property("MARVEL_API_KEY") as? String).orEmpty()
    val marvelApiSecret = (project.property("MARVEL_API_SECRET") as? String).orEmpty()

    buildTypes.forEach {
        it.buildConfigField("String", "MARVEL_BASE_URL", marvelBaseUrl)
        it.buildConfigField("String", "MARVEL_API_KEY", marvelApiKey)
        it.buildConfigField("String", "MARVEL_API_SECRET", marvelApiSecret)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(AppDependencies.appLibraries)
    kapt(AppDependencies.kaptLibraries)
    testImplementation(AppDependencies.testLibraries)
    androidTestImplementation(AppDependencies.androidTestLibraries)
}