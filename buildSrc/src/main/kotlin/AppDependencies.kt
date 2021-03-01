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
    private val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    private val roomKapt = "androidx.room:room-compiler:${Versions.room}"
    private val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    private val swipeRefreshLayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"

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
    private val lottie = "com.airbnb.android:lottie:${Versions.lottie}"

    //Testing
    private val kotlinCoroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutinesTest}"
    private val junit = "junit:junit:${Versions.junit}"
    private val mockWebServer = "com.squareup.okhttp:mockwebserver:${Versions.mockWebServer}"
    private val mockk = "io.mockk:mockk:${Versions.mockk}"
    private val koinTest = "org.koin:koin-test:${Versions.koinTest}"
    private val androidXTestRunner = "androidx.test:runner:${Versions.androidXTestRunner}"
    private val androidXCoreTesting =
        "androidx.arch.core:core-testing:${Versions.androidXCoreTesting}"
    private val androidXJunitTesting = "androidx.test.ext:junit:${Versions.androidXJUnitTest}"

    private val androidXFragmentTesting =
        "androidx.fragment:fragment-testing:${Versions.androidXFragmentTesting}"
    private val mockkAndroid = "io.mockk:mockk-android:${Versions.mockk}"
    private val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    private val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
    private val espressoIdlingResource =
        "androidx.test.espresso:espresso-idling-resource:${Versions.espresso}"
    private val kotestArrow = "io.kotlintest:kotlintest-assertions-arrow:${Versions.kotestArrow}"

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
        add(roomRuntime)
        add(roomKtx)
        add(retrofit)
        add(retrofitGsonConverter)
        add(gson)
        add(coroutinesAndroid)
        add(koinViewModelAndroid)
        add(httpLoggingInterceptor)
        add(arrowCore)
        add(glide)
        add(lottie)
        add(mockk)
        add(koinTest)
        add(androidXTestRunner)
        add(androidXCoreTesting)
        add(androidXJunitTesting)
        add(androidXFragmentTesting)
        add(espressoCore)
        add(espressoContrib)
        add(espressoIdlingResource)
        add(swipeRefreshLayout)
    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(mockkAndroid)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(kotlinCoroutinesTest)
        add(junit)
        add(mockWebServer)
        add(kotestArrow)
    }

    val kaptLibraries = arrayListOf<String>().apply {
        add(roomKapt)
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