# A Movie Streaming Demo App

# Introduction
This project is a streaming App for movies and series.

## App Features
- Connects to a remote api
- User login
- User user logout
- Upgrade of user to premium member
- Displays movies and series
- Streaming of movies
- Allows streaming of premium contents only by premium members
- Auto play next episode in a series
- Highlight of playing movie or episode in a series
- Display of suggested movies after last episode has finished playing

## The main used architectures
* MVVM
* Data Sources
* Data Repository
* Interactors(use-cases)


<p align="center">
    <img src="https://i.imgur.com/P3V0gwq.png" width="600">
</p>

---

## Why MVVM?
* MVVM is one of the architectural patterns which enhances separation of concerns.
* Google introduced architecture components which includes **LiveData** and **ViewModel** which facilitates developing Android app using MVVM pattern.
* It's helpful to solve common problems like **Tight Coupling** and **Testability**.


### Used Jetpack Architecture Components
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Notify views when underlying sharedpreferences changes.
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Manage UI-related data in a lifecycle-conscious way.
* 

---

## Why Data Sources?
* Provides data from multiple sources (DB, API).
* Easily adding new data sources.

## Why Data Repository?
* Decouples the application from the data sources.
* Provides **single source of truth**
* Testable business logic via Unit Tests.

## Why Interactors(use-cases)?
* Sits between [Repository] and [ViewModel] for separation of responsibility 
* Each use-case performs only one function, i.e ***single responsibility*** as described in [S.O.L.I.D] principle. .
* Provide code re-usability
* Testable business logic via Unit Tests.
---

## The app has following packages 
1. **data**: It contains sources and domain repository implementation.
2. **domain**: It contains models and defines data repository.
3. **ui**: View classes along with their corresponding ViewModel and Adapters.
4. **utils**: Utility classes.

---

## How to run
### Requirements
* Android Studio
* Gradle

### Language and IDE
This project was written using Java in Android Studio Dolphin | 2021.3.1 Patch 1

### Prerequisites
This project assumes a base knowledge of Java and Android, such as
Activities, Fragments, RecyclerViews, and the Manifest.

### Steps
To run this application, you need to sync gradle dependencies and run.

There are 5 mock users hard coded in the app.
Login using any of the users below with their corresponding email and password
* Kwame Twum - email: kwame@homi.com", password: asdf1234 => premium user
* Alex - email: tikay@homi.com, password: asdf1234 => premium user
* Sarah - email: sarah@homi.com, password: asdf1234 => normal user
* Adolf - email: adolf@homi.com, password: asdf1234 => normal user
* Fred - email: fred@homi.com, password: asdf1234 => normal user

---


## 3rd Party Libraries 
* [Retrofit](https://square.github.io/retrofit/) - A HTTP client for Android.
* [Logging Interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor) - An OkHttp interceptor which logs HTTP request and response data.
* [GSON](https://github.com/google/gson) - A serialization/deserialization library to convert Objects into JSON and back.
* [Mockito](https://site.mockito.org) - Mocking framework for unit tests.
* [Glide] - Image loading library.
* [ExoPlayer] - Video player library.
* [Butterknife] - view injection library.

