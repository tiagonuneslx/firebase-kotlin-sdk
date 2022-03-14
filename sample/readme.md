# Firebase Kotlin SDK sample

This sample app shows how you can use the Firebase Kotlin SDK library in your Kotlin Multiplatform
Mobile project.

## Setup

Pre-requirements:
- XCode
- Android Studio
- KMM Plugin

1. Verify that you're using ruby 2.*

```zsh
ruby -v
```

IMPORTANT: ruby 3.* will not work, as it's incompatible with `cocoapods-generate`.

NOTE: If you're on a M1-chip Mac, you're recommended to install Ruby, instead of using the system
default, because it comes with a \[universal.arm64e-darwin21\] version instead of a
\[arm64-darwin21\] version. However, be careful, because if you're installing via
`brew install ruby`, it will install Ruby 3.*, which is incompatible with `cocoapods-generate`.

It's probably a good idea to use a ruby version manager, to be able to alternate between different 
Ruby versions, like rvm or rbenv.

1. Install cocoapods
```zsh
gem install cocoapods
```

1. Install cocoapods-generate
```zsh
gem install cocoapods-generate
```

1. Clone the project and open the sample folder on Android Studio

```zsh
git clone https://github.com/GitLiveApp/firebase-kotlin-sdk
```

1. [Optional] If you want to use an unpublished version of the library,
build and publish the library locally.
   
To build and publish locally, it's recommended to open the library root folder on Android Studio, 
and running the Gradle task `publishToMavenLocal` using the Gradle tool window of Android Studio, 
selecting "Tasks", "publishing", "publishToMavenLocal", or adding a new Gradle configuration with
this task.

As an alternative, you can run the following commands on your terminal, inside the project root 
folder. This is not recommended, because it will use the system default JVM, which might be
different from the one used by Android Studio.
```zsh
./gradlew build
./gradlew publishToMavenLocal
```

1. Run the project on Android / iOS

