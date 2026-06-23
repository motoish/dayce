# Android MVP Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Reorganize dayce into sibling `ios/` and `android/` platform folders, then add a lightweight native Android MVP matching the current iOS app behavior.

**Architecture:** Keep iOS behavior unchanged while relocating it under `ios/`. Build Android as a native Kotlin app with a small domain layer, Room persistence, one ViewModel, and Compose screens for list, detail, and form flows.

**Tech Stack:** SwiftUI/SwiftData for existing iOS; Kotlin, Jetpack Compose, Room, AndroidX Navigation, Gradle Kotlin DSL, JUnit for Android unit tests.

---

## File Structure

Existing files to move:

- Move `dayce.xcodeproj/` to `ios/dayce.xcodeproj/`.
- Move `dayce/` to `ios/dayce/`.
- Move `dayceTests/` to `ios/dayceTests/`.
- Leave root `README.md`, `.gitignore`, and docs at the repository root.

Existing files to modify:

- Modify `README.md` to describe both platforms and their run/test commands.
- Modify `ios/dayce.xcodeproj/project.pbxproj` only if Xcode project paths require updates after relocation.
- Modify `ios/dayce.xcodeproj/xcshareddata/xcschemes/dayce.xcscheme` only if Xcode updates the scheme during verification.

Android files to create:

- Create `android/settings.gradle.kts`: Gradle project name and `:app` include.
- Create `android/build.gradle.kts`: Android Gradle Plugin and Kotlin plugin versions.
- Create `android/gradle.properties`: AndroidX and Kotlin settings.
- Create `android/app/build.gradle.kts`: Android app configuration, Compose, Room, tests.
- Create `android/app/src/main/AndroidManifest.xml`: app entry, label `dayce`, theme, launcher icon.
- Create `android/app/src/main/java/com/motoish/dayce/MainActivity.kt`: Compose entry point.
- Create `android/app/src/main/java/com/motoish/dayce/domain/DayEventKind.kt`: event kind enum.
- Create `android/app/src/main/java/com/motoish/dayce/domain/DayCalculator.kt`: count-up/countdown calculations.
- Create `android/app/src/main/java/com/motoish/dayce/data/Converters.kt`: Room converters for dates/kinds.
- Create `android/app/src/main/java/com/motoish/dayce/data/DayEventEntity.kt`: Room entity.
- Create `android/app/src/main/java/com/motoish/dayce/data/DayEventDao.kt`: Room DAO.
- Create `android/app/src/main/java/com/motoish/dayce/data/DayceDatabase.kt`: Room database.
- Create `android/app/src/main/java/com/motoish/dayce/data/DayEventRepository.kt`: repository abstraction over DAO.
- Create `android/app/src/main/java/com/motoish/dayce/ui/DayceApp.kt`: navigation host and app scaffold.
- Create `android/app/src/main/java/com/motoish/dayce/ui/EventViewModel.kt`: UI state and event mutations.
- Create `android/app/src/main/java/com/motoish/dayce/ui/EventListScreen.kt`: list and filter UI.
- Create `android/app/src/main/java/com/motoish/dayce/ui/EventDetailScreen.kt`: event detail UI.
- Create `android/app/src/main/java/com/motoish/dayce/ui/EventFormScreen.kt`: add/edit form UI.
- Create `android/app/src/main/java/com/motoish/dayce/ui/theme/Theme.kt`: Material theme.
- Create Android drawable/mipmap resources for launcher icons from the existing dayce logo.
- Create `android/app/src/test/java/com/motoish/dayce/domain/DayCalculatorTest.kt`: date calculation tests.
- Create `android/app/src/test/java/com/motoish/dayce/ui/EventFilterTest.kt`: filter behavior tests.

---

### Task 1: Relocate iOS Into `ios/`

**Files:**
- Move: `dayce.xcodeproj/` -> `ios/dayce.xcodeproj/`
- Move: `dayce/` -> `ios/dayce/`
- Move: `dayceTests/` -> `ios/dayceTests/`
- Modify: `README.md`

- [ ] **Step 1: Confirm working tree before moving**

Run:

```bash
cd /Users/tanishi-m5p/repository/dayce
git status -sb
git diff -- dayce.xcodeproj/project.pbxproj dayce.xcodeproj/xcshareddata/xcschemes/dayce.xcscheme
```

Expected: the Android design spec commit is present on `main`; the only pre-existing dirty files are the Xcode project/scheme changes caused by local Xcode. Preserve them during the move.

- [ ] **Step 2: Move iOS files**

Run:

```bash
cd /Users/tanishi-m5p/repository/dayce
mkdir -p ios
git mv dayce.xcodeproj ios/dayce.xcodeproj
git mv dayce ios/dayce
git mv dayceTests ios/dayceTests
```

Expected: `git status -sb` shows renames into `ios/` and no deleted iOS source files without matching additions.

- [ ] **Step 3: Update README for platform folders**

Replace `README.md` with:

```markdown
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
```

- [ ] **Step 4: Verify iOS build and tests after relocation**

Run:

```bash
cd /Users/tanishi-m5p/repository/dayce
xcodebuild test -project ios/dayce.xcodeproj -scheme dayce -destination 'platform=iOS Simulator,name=iPhone 17' -derivedDataPath ios/DerivedData
xcodebuild build -project ios/dayce.xcodeproj -scheme dayce -destination 'platform=iOS Simulator,name=iPad Pro 13-inch (M4)' -derivedDataPath ios/DerivedData
```

Expected: iPhone tests pass and iPad build succeeds. If simulator names differ locally, run `xcrun simctl list devices available` and use the closest available iPhone/iPad simulator.

- [ ] **Step 5: Commit iOS relocation**

Run:

```bash
cd /Users/tanishi-m5p/repository/dayce
git status -sb
git add README.md ios/dayce.xcodeproj ios/dayce ios/dayceTests
git commit -m "Move iOS app into platform folder"
```

Expected: commit contains the iOS relocation and README update only.

---

### Task 2: Scaffold Android Gradle Project

**Files:**
- Create: `android/settings.gradle.kts`
- Create: `android/build.gradle.kts`
- Create: `android/gradle.properties`
- Create: `android/app/build.gradle.kts`
- Create: `android/app/src/main/AndroidManifest.xml`
- Create: `android/app/src/main/java/com/motoish/dayce/MainActivity.kt`
- Create: `android/app/src/main/java/com/motoish/dayce/ui/theme/Theme.kt`

- [ ] **Step 1: Create Gradle settings**

Create `android/settings.gradle.kts`:

```kotlin
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "dayce-android"
include(":app")
```

- [ ] **Step 2: Create root Gradle build file**

Create `android/build.gradle.kts`:

```kotlin
plugins {
    id("com.android.application") version "8.7.3" apply false
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
}
```

- [ ] **Step 3: Create Gradle properties**

Create `android/gradle.properties`:

```properties
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
android.useAndroidX=true
android.nonTransitiveRClass=true
kotlin.code.style=official
```

- [ ] **Step 4: Create app build file**

Create `android/app/build.gradle.kts`:

```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.motoish.dayce"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.motoish.dayce"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    val roomVersion = "2.6.1"
    val navVersion = "2.8.5"
    val lifecycleVersion = "2.8.7"

    implementation(platform("androidx.compose:compose-bom:2024.12.01"))
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation("androidx.navigation:navigation-compose:$navVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    debugImplementation("androidx.compose.ui:ui-tooling")
    testImplementation("junit:junit:4.13.2")
}
```

- [ ] **Step 5: Create manifest and entry activity**

Create `android/app/src/main/AndroidManifest.xml`:

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <application
        android:name=".DayceApplication"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="dayce"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.Dayce">
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:theme="@style/Theme.Dayce">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```

Create `android/app/src/main/java/com/motoish/dayce/MainActivity.kt`:

```kotlin
package com.motoish.dayce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.motoish.dayce.ui.DayceApp
import com.motoish.dayce.ui.theme.DayceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DayceTheme {
                DayceApp()
            }
        }
    }
}
```

Create `android/app/src/main/java/com/motoish/dayce/ui/theme/Theme.kt`:

```kotlin
package com.motoish.dayce.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DayceColors = lightColorScheme(
    primary = Color(0xFF2563EB),
    secondary = Color(0xFF0F766E),
    tertiary = Color(0xFFB45309),
    surface = Color(0xFFFFFBFE),
    background = Color(0xFFFFFBFE)
)

@Composable
fun DayceTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DayceColors,
        typography = MaterialTheme.typography,
        content = content
    )
}
```

- [ ] **Step 6: Add minimal style resource**

Create `android/app/src/main/res/values/styles.xml`:

```xml
<resources>
    <style name="Theme.Dayce" parent="android:style/Theme.Material.Light.NoActionBar" />
</resources>
```

- [ ] **Step 7: Commit scaffold**

Run:

```bash
cd /Users/tanishi-m5p/repository/dayce
git add android/settings.gradle.kts android/build.gradle.kts android/gradle.properties android/app/build.gradle.kts android/app/src/main/AndroidManifest.xml android/app/src/main/java/com/motoish/dayce/MainActivity.kt android/app/src/main/java/com/motoish/dayce/ui/theme/Theme.kt android/app/src/main/res/values/styles.xml
git commit -m "Scaffold Android project"
```

Expected: Android project files are committed without generated build outputs.

---

### Task 3: Add Android Domain Logic With Tests

**Files:**
- Create: `android/app/src/main/java/com/motoish/dayce/domain/DayEventKind.kt`
- Create: `android/app/src/main/java/com/motoish/dayce/domain/DayCalculator.kt`
- Create: `android/app/src/test/java/com/motoish/dayce/domain/DayCalculatorTest.kt`
- Create: `android/app/src/main/java/com/motoish/dayce/ui/EventFilter.kt`
- Create: `android/app/src/test/java/com/motoish/dayce/ui/EventFilterTest.kt`

- [ ] **Step 1: Write date calculation tests**

Create `android/app/src/test/java/com/motoish/dayce/domain/DayCalculatorTest.kt`:

```kotlin
package com.motoish.dayce.domain

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class DayCalculatorTest {
    @Test
    fun countUpIncludesEventDateAsDayOne() {
        val today = LocalDate.of(2026, 6, 24)
        val eventDate = LocalDate.of(2026, 6, 24)

        val result = DayCalculator.dayCount(DayEventKind.CountUp, eventDate, today)

        assertEquals(1, result)
    }

    @Test
    fun countUpTomorrowIsZero() {
        val today = LocalDate.of(2026, 6, 24)
        val eventDate = LocalDate.of(2026, 6, 25)

        val result = DayCalculator.dayCount(DayEventKind.CountUp, eventDate, today)

        assertEquals(0, result)
    }

    @Test
    fun countdownTargetDateIsZero() {
        val today = LocalDate.of(2026, 6, 24)
        val targetDate = LocalDate.of(2026, 6, 24)

        val result = DayCalculator.dayCount(DayEventKind.Countdown, targetDate, today)

        assertEquals(0, result)
    }

    @Test
    fun countdownFutureDateIsPositive() {
        val today = LocalDate.of(2026, 6, 24)
        val targetDate = LocalDate.of(2026, 6, 30)

        val result = DayCalculator.dayCount(DayEventKind.Countdown, targetDate, today)

        assertEquals(6, result)
    }

    @Test
    fun countdownPastDateIsNegative() {
        val today = LocalDate.of(2026, 6, 24)
        val targetDate = LocalDate.of(2026, 6, 20)

        val result = DayCalculator.dayCount(DayEventKind.Countdown, targetDate, today)

        assertEquals(-4, result)
    }
}
```

- [ ] **Step 2: Run domain tests to verify they fail**

Run:

```bash
cd /Users/tanishi-m5p/repository/dayce/android
./gradlew testDebugUnitTest --tests com.motoish.dayce.domain.DayCalculatorTest
```

Expected: fails because `DayCalculator` and `DayEventKind` do not exist.

- [ ] **Step 3: Implement domain types**

Create `android/app/src/main/java/com/motoish/dayce/domain/DayEventKind.kt`:

```kotlin
package com.motoish.dayce.domain

enum class DayEventKind {
    CountUp,
    Countdown
}
```

Create `android/app/src/main/java/com/motoish/dayce/domain/DayCalculator.kt`:

```kotlin
package com.motoish.dayce.domain

import java.time.LocalDate
import java.time.temporal.ChronoUnit

object DayCalculator {
    fun dayCount(kind: DayEventKind, eventDate: LocalDate, today: LocalDate): Long {
        return when (kind) {
            DayEventKind.CountUp -> {
                val days = ChronoUnit.DAYS.between(eventDate, today) + 1
                days.coerceAtLeast(0)
            }
            DayEventKind.Countdown -> ChronoUnit.DAYS.between(today, eventDate)
        }
    }
}
```

- [ ] **Step 4: Write filter tests**

Create `android/app/src/test/java/com/motoish/dayce/ui/EventFilterTest.kt`:

```kotlin
package com.motoish.dayce.ui

import com.motoish.dayce.domain.DayEventKind
import org.junit.Assert.assertEquals
import org.junit.Test

class EventFilterTest {
    @Test
    fun allFilterKeepsEveryEvent() {
        val events = listOf(
            FilterableEvent(1, DayEventKind.CountUp),
            FilterableEvent(2, DayEventKind.Countdown)
        )

        val result = EventFilter.All.apply(events)

        assertEquals(listOf(1L, 2L), result.map { it.id })
    }

    @Test
    fun countUpFilterKeepsOnlyCountUpEvents() {
        val events = listOf(
            FilterableEvent(1, DayEventKind.CountUp),
            FilterableEvent(2, DayEventKind.Countdown)
        )

        val result = EventFilter.CountUp.apply(events)

        assertEquals(listOf(1L), result.map { it.id })
    }

    @Test
    fun countdownFilterKeepsOnlyCountdownEvents() {
        val events = listOf(
            FilterableEvent(1, DayEventKind.CountUp),
            FilterableEvent(2, DayEventKind.Countdown)
        )

        val result = EventFilter.Countdown.apply(events)

        assertEquals(listOf(2L), result.map { it.id })
    }
}
```

- [ ] **Step 5: Implement filter contract**

Create `android/app/src/main/java/com/motoish/dayce/ui/EventFilter.kt`:

```kotlin
package com.motoish.dayce.ui

import com.motoish.dayce.domain.DayEventKind

interface FilterableEvent {
    val id: Long
    val kind: DayEventKind
}

enum class EventFilter {
    All,
    CountUp,
    Countdown;

    fun <T : FilterableEvent> apply(events: List<T>): List<T> {
        return when (this) {
            All -> events
            CountUp -> events.filter { it.kind == DayEventKind.CountUp }
            Countdown -> events.filter { it.kind == DayEventKind.Countdown }
        }
    }
}
```

- [ ] **Step 6: Run unit tests**

Run:

```bash
cd /Users/tanishi-m5p/repository/dayce/android
./gradlew testDebugUnitTest --tests com.motoish.dayce.domain.DayCalculatorTest --tests com.motoish.dayce.ui.EventFilterTest
```

Expected: all tests pass.

- [ ] **Step 7: Commit domain layer**

Run:

```bash
cd /Users/tanishi-m5p/repository/dayce
git add android/app/src/main/java/com/motoish/dayce/domain android/app/src/main/java/com/motoish/dayce/ui/EventFilter.kt android/app/src/test/java/com/motoish/dayce/domain android/app/src/test/java/com/motoish/dayce/ui
git commit -m "Add Android day calculation logic"
```

Expected: commit contains domain logic and unit tests.

---

### Task 4: Add Room Persistence

**Files:**
- Create: `android/app/src/main/java/com/motoish/dayce/DayceApplication.kt`
- Create: `android/app/src/main/java/com/motoish/dayce/data/Converters.kt`
- Create: `android/app/src/main/java/com/motoish/dayce/data/DayEventEntity.kt`
- Create: `android/app/src/main/java/com/motoish/dayce/data/DayEventDao.kt`
- Create: `android/app/src/main/java/com/motoish/dayce/data/DayceDatabase.kt`
- Create: `android/app/src/main/java/com/motoish/dayce/data/DayEventRepository.kt`

- [ ] **Step 1: Create Room converters**

Create `android/app/src/main/java/com/motoish/dayce/data/Converters.kt`:

```kotlin
package com.motoish.dayce.data

import androidx.room.TypeConverter
import com.motoish.dayce.domain.DayEventKind
import java.time.Instant
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun localDateToString(value: LocalDate): String = value.toString()

    @TypeConverter
    fun stringToLocalDate(value: String): LocalDate = LocalDate.parse(value)

    @TypeConverter
    fun instantToString(value: Instant): String = value.toString()

    @TypeConverter
    fun stringToInstant(value: String): Instant = Instant.parse(value)

    @TypeConverter
    fun eventKindToString(value: DayEventKind): String = value.name

    @TypeConverter
    fun stringToEventKind(value: String): DayEventKind = DayEventKind.valueOf(value)
}
```

- [ ] **Step 2: Create entity**

Create `android/app/src/main/java/com/motoish/dayce/data/DayEventEntity.kt`:

```kotlin
package com.motoish.dayce.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.motoish.dayce.domain.DayEventKind
import com.motoish.dayce.ui.FilterableEvent
import java.time.Instant
import java.time.LocalDate

@Entity(tableName = "day_events")
data class DayEventEntity(
    @PrimaryKey(autoGenerate = true) override val id: Long = 0,
    val name: String,
    val date: LocalDate,
    override val kind: DayEventKind,
    val note: String?,
    val createdAt: Instant,
    val updatedAt: Instant
) : FilterableEvent
```

- [ ] **Step 3: Create DAO**

Create `android/app/src/main/java/com/motoish/dayce/data/DayEventDao.kt`:

```kotlin
package com.motoish.dayce.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DayEventDao {
    @Query("SELECT * FROM day_events ORDER BY date ASC, name COLLATE NOCASE ASC")
    fun observeAll(): Flow<List<DayEventEntity>>

    @Query("SELECT * FROM day_events WHERE id = :id LIMIT 1")
    fun observeById(id: Long): Flow<DayEventEntity?>

    @Insert
    suspend fun insert(event: DayEventEntity): Long

    @Update
    suspend fun update(event: DayEventEntity)

    @Delete
    suspend fun delete(event: DayEventEntity)
}
```

- [ ] **Step 4: Create database**

Create `android/app/src/main/java/com/motoish/dayce/data/DayceDatabase.kt`:

```kotlin
package com.motoish.dayce.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DayEventEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DayceDatabase : RoomDatabase() {
    abstract fun dayEventDao(): DayEventDao
}
```

- [ ] **Step 5: Create repository**

Create `android/app/src/main/java/com/motoish/dayce/data/DayEventRepository.kt`:

```kotlin
package com.motoish.dayce.data

import com.motoish.dayce.domain.DayEventKind
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import java.time.LocalDate

class DayEventRepository(private val dao: DayEventDao) {
    fun observeAll(): Flow<List<DayEventEntity>> = dao.observeAll()

    fun observeById(id: Long): Flow<DayEventEntity?> = dao.observeById(id)

    suspend fun add(name: String, date: LocalDate, kind: DayEventKind, note: String?) {
        val now = Instant.now()
        dao.insert(
            DayEventEntity(
                name = name.trim(),
                date = date,
                kind = kind,
                note = note?.trim()?.ifBlank { null },
                createdAt = now,
                updatedAt = now
            )
        )
    }

    suspend fun update(existing: DayEventEntity, name: String, date: LocalDate, kind: DayEventKind, note: String?) {
        dao.update(
            existing.copy(
                name = name.trim(),
                date = date,
                kind = kind,
                note = note?.trim()?.ifBlank { null },
                updatedAt = Instant.now()
            )
        )
    }

    suspend fun delete(event: DayEventEntity) {
        dao.delete(event)
    }
}
```

- [ ] **Step 6: Create application container**

Create `android/app/src/main/java/com/motoish/dayce/DayceApplication.kt`:

```kotlin
package com.motoish.dayce

import android.app.Application
import androidx.room.Room
import com.motoish.dayce.data.DayEventRepository
import com.motoish.dayce.data.DayceDatabase

class DayceApplication : Application() {
    val database: DayceDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            DayceDatabase::class.java,
            "dayce.db"
        ).build()
    }

    val repository: DayEventRepository by lazy {
        DayEventRepository(database.dayEventDao())
    }
}
```

- [ ] **Step 7: Build compile check**

Run:

```bash
cd /Users/tanishi-m5p/repository/dayce/android
./gradlew assembleDebug
```

Expected: build succeeds or fails only because UI route classes are still minimal. Fix imports and KSP issues before moving on.

- [ ] **Step 8: Commit persistence layer**

Run:

```bash
cd /Users/tanishi-m5p/repository/dayce
git add android/app/src/main/java/com/motoish/dayce/DayceApplication.kt android/app/src/main/java/com/motoish/dayce/data
git commit -m "Add Android local persistence"
```

Expected: Room persistence layer is committed.

---

### Task 5: Add Android Compose App Flow

**Files:**
- Create: `android/app/src/main/java/com/motoish/dayce/ui/DayceApp.kt`
- Create: `android/app/src/main/java/com/motoish/dayce/ui/EventViewModel.kt`
- Create: `android/app/src/main/java/com/motoish/dayce/ui/EventListScreen.kt`
- Create: `android/app/src/main/java/com/motoish/dayce/ui/EventDetailScreen.kt`
- Create: `android/app/src/main/java/com/motoish/dayce/ui/EventFormScreen.kt`

- [ ] **Step 1: Create ViewModel state and actions**

Create `android/app/src/main/java/com/motoish/dayce/ui/EventViewModel.kt`:

```kotlin
package com.motoish.dayce.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.motoish.dayce.data.DayEventEntity
import com.motoish.dayce.data.DayEventRepository
import com.motoish.dayce.domain.DayEventKind
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class EventViewModel(private val repository: DayEventRepository) : ViewModel() {
    var filter: EventFilter = EventFilter.All
        private set

    val events: StateFlow<List<DayEventEntity>> = repository.observeAll()
        .map { filter.apply(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun setFilter(next: EventFilter) {
        filter = next
    }

    fun observeEvent(id: Long) = repository.observeById(id)

    fun addEvent(name: String, date: LocalDate, kind: DayEventKind, note: String?) {
        if (name.isBlank()) return
        viewModelScope.launch {
            repository.add(name, date, kind, note)
        }
    }

    fun updateEvent(event: DayEventEntity, name: String, date: LocalDate, kind: DayEventKind, note: String?) {
        if (name.isBlank()) return
        viewModelScope.launch {
            repository.update(event, name, date, kind, note)
        }
    }

    fun deleteEvent(event: DayEventEntity) {
        viewModelScope.launch {
            repository.delete(event)
        }
    }

    class Factory(private val repository: DayEventRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EventViewModel(repository) as T
        }
    }
}
```

- [ ] **Step 2: Create navigation host**

Create `android/app/src/main/java/com/motoish/dayce/ui/DayceApp.kt`:

```kotlin
package com.motoish.dayce.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.motoish.dayce.DayceApplication

@Composable
fun DayceApp() {
    val navController = rememberNavController()
    val app = LocalContext.current.applicationContext as DayceApplication
    val viewModel: EventViewModel = viewModel(factory = EventViewModel.Factory(app.repository))

    NavHost(navController = navController, startDestination = "events") {
        composable("events") {
            EventListScreen(
                viewModel = viewModel,
                onAdd = { navController.navigate("event/new") },
                onOpen = { navController.navigate("event/$it") }
            )
        }
        composable("event/new") {
            EventFormScreen(
                title = "New event",
                existing = null,
                onCancel = { navController.popBackStack() },
                onSave = { name, date, kind, note ->
                    viewModel.addEvent(name, date, kind, note)
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = "event/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { entry ->
            val id = entry.arguments?.getLong("id") ?: return@composable
            EventDetailScreen(
                viewModel = viewModel,
                eventId = id,
                onBack = { navController.popBackStack() },
                onEdit = { navController.navigate("event/$id/edit") },
                onDeleted = { navController.popBackStack("events", false) }
            )
        }
        composable(
            route = "event/{id}/edit",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { entry ->
            val id = entry.arguments?.getLong("id") ?: return@composable
            EventEditRoute(
                viewModel = viewModel,
                eventId = id,
                onCancel = { navController.popBackStack() },
                onSaved = { navController.popBackStack() }
            )
        }
    }
}
```

- [ ] **Step 3: Create list screen**

Create `android/app/src/main/java/com/motoish/dayce/ui/EventListScreen.kt` with a `Scaffold`, top app bar title `dayce`, a segmented filter row for `All`, `CountUp`, `Countdown`, a `LazyColumn` of events, and a floating add button. Each row displays name, date, event kind, and `DayCalculator.dayCount(event.kind, event.date, LocalDate.now())`. Row click calls `onOpen(event.id)`.

Use these function signatures exactly:

```kotlin
@Composable
fun EventListScreen(
    viewModel: EventViewModel,
    onAdd: () -> Unit,
    onOpen: (Long) -> Unit
)
```

```kotlin
@Composable
private fun EventRow(
    event: DayEventEntity,
    today: LocalDate,
    onClick: () -> Unit
)
```

- [ ] **Step 4: Create detail screen**

Create `android/app/src/main/java/com/motoish/dayce/ui/EventDetailScreen.kt` with:

```kotlin
@Composable
fun EventDetailScreen(
    viewModel: EventViewModel,
    eventId: Long,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDeleted: () -> Unit
)
```

Behavior:

- Collect `viewModel.observeEvent(eventId)` as state.
- Show back, edit, and delete icon buttons.
- Show name, date, kind, note, and calculated day count.
- Delete calls `viewModel.deleteEvent(event)` then `onDeleted()`.
- If event is missing, show a simple empty state and a back action.

- [ ] **Step 5: Create form screen**

Create `android/app/src/main/java/com/motoish/dayce/ui/EventFormScreen.kt` with:

```kotlin
@Composable
fun EventFormScreen(
    title: String,
    existing: DayEventEntity?,
    onCancel: () -> Unit,
    onSave: (String, LocalDate, DayEventKind, String?) -> Unit
)
```

Behavior:

- `OutlinedTextField` for name.
- `OutlinedTextField` for ISO date string `yyyy-MM-dd`.
- Two selectable buttons or chips for count-up/countdown.
- `OutlinedTextField` for optional note.
- Save disabled when name is blank or date cannot parse as `LocalDate`.

Also create:

```kotlin
@Composable
fun EventEditRoute(
    viewModel: EventViewModel,
    eventId: Long,
    onCancel: () -> Unit,
    onSaved: () -> Unit
)
```

This route collects the event and passes it to `EventFormScreen`. Save calls `viewModel.updateEvent(existing, name, date, kind, note)`.

- [ ] **Step 6: Compile and fix UI issues**

Run:

```bash
cd /Users/tanishi-m5p/repository/dayce/android
./gradlew testDebugUnitTest assembleDebug
```

Expected: unit tests pass and debug APK builds. Fix missing imports and Compose API mismatches before committing.

- [ ] **Step 7: Commit Android UI flow**

Run:

```bash
cd /Users/tanishi-m5p/repository/dayce
git add android/app/src/main/java/com/motoish/dayce/ui android/app/src/main/java/com/motoish/dayce/MainActivity.kt
git commit -m "Add Android event screens"
```

Expected: commit contains Compose navigation and screens.

---

### Task 6: Add Android Icon Assets and Final Verification

**Files:**
- Create/modify: `android/app/src/main/res/mipmap-*/*`
- Create/modify: `android/app/src/main/res/drawable/*`
- Modify: `android/app/src/main/AndroidManifest.xml`
- Modify: `.gitignore` if Android build outputs are not already ignored
- Modify: `README.md` if run instructions changed during implementation

- [ ] **Step 1: Generate launcher assets from existing logo**

Use the existing source logo at `/Users/tanishi-m5p/Downloads/dayce_dm_sans_icon_v2.svg` or the already-rendered iOS app icon at `ios/dayce/Assets.xcassets/AppIcon.appiconset/AppIcon-1024.png`.

Generate Android icon PNGs for these paths:

```text
android/app/src/main/res/mipmap-mdpi/ic_launcher.png
android/app/src/main/res/mipmap-hdpi/ic_launcher.png
android/app/src/main/res/mipmap-xhdpi/ic_launcher.png
android/app/src/main/res/mipmap-xxhdpi/ic_launcher.png
android/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png
android/app/src/main/res/mipmap-mdpi/ic_launcher_round.png
android/app/src/main/res/mipmap-hdpi/ic_launcher_round.png
android/app/src/main/res/mipmap-xhdpi/ic_launcher_round.png
android/app/src/main/res/mipmap-xxhdpi/ic_launcher_round.png
android/app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png
```

Sizes:

```text
mdpi: 48x48
hdpi: 72x72
xhdpi: 96x96
xxhdpi: 144x144
xxxhdpi: 192x192
```

- [ ] **Step 2: Ensure generated files are tracked and build outputs are ignored**

Check `.gitignore` contains Android build ignores:

```gitignore
.gradle/
build/
**/build/
local.properties
```

If any are missing, add them.

- [ ] **Step 3: Run full iOS verification**

Run:

```bash
cd /Users/tanishi-m5p/repository/dayce
xcodebuild test -project ios/dayce.xcodeproj -scheme dayce -destination 'platform=iOS Simulator,name=iPhone 17' -derivedDataPath ios/DerivedData
xcodebuild build -project ios/dayce.xcodeproj -scheme dayce -destination 'platform=iOS Simulator,name=iPad Pro 13-inch (M4)' -derivedDataPath ios/DerivedData
```

Expected: iPhone tests pass and iPad build succeeds.

- [ ] **Step 4: Run full Android verification**

Run:

```bash
cd /Users/tanishi-m5p/repository/dayce/android
./gradlew testDebugUnitTest assembleDebug
```

Expected: Android unit tests pass and debug APK builds.

- [ ] **Step 5: Commit final assets and docs**

Run:

```bash
cd /Users/tanishi-m5p/repository/dayce
git add .gitignore README.md android/app/src/main/res android/app/src/main/AndroidManifest.xml
git commit -m "Add Android app icon and docs"
```

Expected: final commit includes icon assets, ignore updates, and documentation updates.

- [ ] **Step 6: Push completed work**

Run:

```bash
cd /Users/tanishi-m5p/repository/dayce
git status -sb
git push origin main
```

Expected: `main` pushes to `git@github.com:motoish/dayce.git`.

---

## Self-Review

Spec coverage:

- Repository layout is covered by Task 1.
- Android Kotlin + Compose + Room stack is covered by Tasks 2, 4, and 5.
- iOS behavior preservation is covered by Task 1 verification and Task 6 final verification.
- Count-up/countdown rules are covered by Task 3 tests.
- Add/edit/delete/detail/list/filter local MVP is covered by Tasks 4 and 5.
- Logo reuse is covered by Task 6.
- No backend/PocketBase work is included.

Placeholder scan:

- No unresolved planning markers.
- No unspecified file paths.
- UI screen tasks define exact function signatures and required behavior.

Type consistency:

- `DayEventKind.CountUp` and `DayEventKind.Countdown` are used consistently.
- `DayEventEntity.id` and `DayEventEntity.kind` satisfy `FilterableEvent`.
- Navigation routes use `Long` IDs consistently.
