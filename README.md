# openbank-android-mobile-test
Openbank Android Mobile Test

## Purpose
This is the android app for the Openbank Mobile Test. It consists of a master-detail app for showing Marvel characters. Because of its simplicity it is not a multi-module app but for larger and complex applications it should be done in that way.

## Architecture
This app is developed based on a clean architecture basis (multilayered with presentation, domain and data layers), followed by a MVVM architectural pattern. It follows SOLID principles and some other software patterns such as Repository pattern, Observer pattern, UseCase Pattern or Delegation pattern. Also, as view states are treated in a machine-state-based way, this pattern could be considered more close to an MVI one.

## Considerations for development

Min API level is set to [`21`](https://android-arsenal.com/api?level=21), so the presented approach is suitable for over
[85% of devices](https://developer.android.com/about/dashboards) running Android. This project takes advantage of latest
popular libraries and tools of the Android ecosystem

* Tech-stack
    * [Kotlin](https://kotlinlang.org/) + [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operations
    * [Kotlin Flow](https://kotlinlang.org/) - Handles asynchronous stream of data without coupling non UI layers with lifecycle aware components as LiveData (https://betterprogramming.pub/no-more-livedata-in-repositories-in-kotlin-85f5a234a8fe) 
    * [Kotlin DSL for Gradle](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - Handle dependencies management and project config gradle files with Kotlin DSL support
    * [Koin](https://insert-koin.io/) - dependency injection for this small project and simple setup
    * [Retrofit](https://square.github.io/retrofit/) - networking
    * [Jetpack](https://developer.android.com/jetpack)
        * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) - deal with whole in-app navigation
        * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - lets the components in your app, usually the UI, observe LiveData objects for changes.
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform action when lifecycle state changes
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way
        * [Room](https://developer.android.com/topic/libraries/architecture/room) - create a cache of your app's data on a device that's running your app
  	* [Glide](https://bumptech.github.io/glide/) - image loading library
* Architecture
    * Clean Architecture
    * MVVM + View Binding (presentation layer)
    * [Android Architecture components](https://developer.android.com/topic/libraries/architecture) ([ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), [LiveData](https://developer.android.com/topic/libraries/architecture/livedata), [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation))
* Tests
    * [Unit Tests](https://en.wikipedia.org/wiki/Unit_testing) ([JUnit](https://junit.org/junit4/))
    * [Mockk](https://mockk.io)
    * [Espresso (UI)](https://developer.android.com/training/testing/espresso)
