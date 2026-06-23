# dayce Android and Repository Layout Design

## Goal

Add a lightweight native Android version of dayce while reorganizing the repository so iOS and Android are first-class sibling platforms.

The current iOS app remains the source of product behavior for the Android MVP. Android should match the iOS MVP rather than introduce new product concepts.

## Repository Layout

Target layout:

```text
dayce/
  README.md
  ios/
    dayce.xcodeproj
    dayce/
    dayceTests/
  android/
    settings.gradle.kts
    build.gradle.kts
    app/
      build.gradle.kts
      src/main/...
```

Migration rules:

- Move the existing iOS project files into `ios/`.
- Keep the existing iOS product code behavior unchanged during the move.
- Update project paths only where required by Xcode after the move.
- Keep root-level docs platform-neutral, with separate iOS and Android run instructions.
- Do not stage unrelated local Xcode auto-upgrade changes unless they are required by the relocation.

## Android Platform Choice

Use a native Android stack:

- Kotlin
- Jetpack Compose
- AndroidX Navigation for Compose
- Room for local persistence
- Gradle Kotlin DSL
- Minimum SDK: Android 8.0 / API 26
- App display name: `dayce`

This keeps the app lightweight on-device and avoids adding a cross-platform runtime. The repository stays balanced because each platform owns its own native project under a dedicated folder.

## Android MVP Scope

Android should include feature parity with the current iOS MVP:

- Record count-up days and countdown days.
- Name each event.
- Store event date, kind, and optional note.
- Show all events in a list.
- Filter by all / count-up / countdown.
- Open an event detail screen.
- Add, edit, and delete events.
- Persist data locally on device.

Out of scope for the Android MVP:

- Account login or cloud sync.
- PocketBase or any backend integration.
- CSV export/import UI.
- Home-screen widgets.
- Notifications.
- Categories, pinning, custom themes, or advanced sorting.

## Date Rules

Use calendar dates, not instants, for all day calculations.

- Count-up events: the event date itself is day 1.
- Countdown events: the target date itself is day 0.
- Past countdown dates show negative values.
- Use `java.time.LocalDate` and `ChronoUnit.DAYS`.

This avoids timezone and daylight-saving bugs caused by calculating with timestamps.

## Android Architecture

Suggested package structure:

```text
app/src/main/java/.../dayce/
  data/
    DayEventEntity.kt
    DayEventDao.kt
    DayceDatabase.kt
    DayEventRepository.kt
  domain/
    DayEventKind.kt
    DayCalculator.kt
  ui/
    DayceApp.kt
    EventListScreen.kt
    EventDetailScreen.kt
    EventFormScreen.kt
    EventViewModel.kt
    theme/
```

Data model fields:

- `id: Long`
- `name: String`
- `date: LocalDate`
- `kind: DayEventKind`
- `note: String?`
- `createdAt: Instant`
- `updatedAt: Instant`

Room should persist `LocalDate` through a type converter, for example ISO-8601 date strings.

## UI Behavior

Phone layout:

- Main screen is the event list.
- Selecting an event opens a detail screen.
- Add and edit use a form screen.
- Delete returns to the list.

Tablet layout can initially reuse the phone navigation. A split layout can be added later after the Android MVP is stable.

Visual direction:

- Keep the interface quiet and practical.
- Reuse the existing dayce app icon source for Android launcher assets.
- Match the iOS app conceptually, not pixel-for-pixel.

## Verification

After moving iOS into `ios/`, verify iOS still works:

```sh
xcodebuild test -project ios/dayce.xcodeproj -scheme dayce -destination 'platform=iOS Simulator,name=iPhone 17' -derivedDataPath ios/DerivedData
xcodebuild build -project ios/dayce.xcodeproj -scheme dayce -destination 'platform=iOS Simulator,name=iPad Pro 13-inch (M4)' -derivedDataPath ios/DerivedData
```

After adding Android, verify Android builds and tests:

```sh
cd android
./gradlew test
./gradlew assembleDebug
```

If Gradle or Android dependencies need network access during setup, request approval before downloading.

## Implementation Order

1. Move iOS project into `ios/` and update documentation.
2. Verify iOS tests/build still pass after relocation.
3. Scaffold Android Gradle project under `android/`.
4. Add Android data/domain layers with tests for date calculations.
5. Add Compose list/detail/form UI.
6. Add Android launcher icon assets from the existing dayce logo.
7. Run Android unit tests and debug build.
8. Commit and push the completed platform structure and Android MVP.
