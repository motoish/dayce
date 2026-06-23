# dayce

Lightweight native app for count-up and countdown days.

## Platforms

- `ios/`: native iOS/iPadOS app built with SwiftUI and SwiftData.
- `android/`: native Android app built with Kotlin, Jetpack Compose, and Room.

## Features

- Add, edit, and delete count-up/countdown events
- Local-only persistence
- Count-up day includes the event date as day 1
- Countdown target date shows 0 days
- No account login or cloud sync

## iOS/iPadOS

Requirements:

- Xcode 16 or newer
- iOS/iPadOS 17 or newer

Open `ios/dayce.xcodeproj` in Xcode or run:

```bash
xcodebuild -project ios/dayce.xcodeproj -scheme dayce -destination 'platform=iOS Simulator,name=iPhone 17' build
```

Run tests:

```bash
xcodebuild test -project ios/dayce.xcodeproj -scheme dayce -destination 'platform=iOS Simulator,name=iPhone 17'
```

## Android

Requirements:

- Android Studio
- Android SDK with the compile SDK configured in `android/app/build.gradle.kts`

Run from the Android project folder:

```bash
cd android
./gradlew test
./gradlew assembleDebug
```
