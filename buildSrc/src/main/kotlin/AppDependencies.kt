import org.gradle.api.artifacts.dsl.DependencyHandler

object AppDependencies {
    // Kotlin
    val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"

    //Android
    private val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    private val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    private val materialDesign = "com.google.android.material:material:${Versions.materialDesign}"
    private val lifecycleKtx =
        "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleKtx}"
    private val lifecycleJava8 =
        "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycleJava8}"

    private val viewModelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModelKtx}"
    private val liveDataKtx =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.liveDataKtx}"
    private val navigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationFragmentKtx}"
    private val navigationUiKtx =
        "androidx.navigation:navigation-ui-ktx:${Versions.navigationUiKtx}"

    //Frameworks
    private val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    private val retrofitGsonConverter =
        "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    private val gson = "com.google.code.gson:gson:${Versions.gson}"
    private val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    private val koin = "org.koin:koin-android:${Versions.koin}"
    private val koinViewModelAndroid = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    private val httpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.httpLoggingInterceptor}"
    private val arrowCore = "io.arrow-kt:arrow-core-data:${Versions.arrowCore}"
    private val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    private val glideCompilerKapt = "com.github.bumptech.glide:compiler:${Versions.glide}"

    //Testing
    private val junit = "junit:junit:${Versions.junit}"
    private val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    private val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    val appLibraries = arrayListOf<String>().apply {
        add(kotlinStdLib)
        add(coreKtx)
        add(appcompat)
        add(constraintLayout)
        add(materialDesign)
        add(lifecycleKtx)
        add(lifecycleJava8)
        add(viewModelKtx)
        add(liveDataKtx)
        add(navigationFragmentKtx)
        add(navigationUiKtx)
        add(retrofit)
        add(retrofitGsonConverter)
        add(gson)
        add(coroutinesAndroid)
        add(koinViewModelAndroid)
        add(httpLoggingInterceptor)
        add(arrowCore)
        add(glide)
    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(junit)
    }

    val kaptLibraries = arrayListOf<String>().apply {
        add(glideCompilerKapt)
    }

}

fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}