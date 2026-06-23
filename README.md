# dayce

Lightweight native iOS/iPadOS app for count-up and countdown days.

## Features

- Add, edit, and delete count-up/countdown events
- Local SwiftData persistence
- Native iPhone list and iPadOS split-view layout
- Count-up day includes the event date as day 1
- Countdown target date shows 0 days

## Requirements

- Xcode 16 or newer
- iOS/iPadOS 17 or newer

## Build

Open `dayce.xcodeproj` in Xcode or run:

```bash
xcodebuild -project dayce.xcodeproj -scheme dayce -destination 'platform=iOS Simulator,name=iPhone 16' build
```

## Test

```bash
xcodebuild test -project dayce.xcodeproj -scheme dayce -destination 'platform=iOS Simulator,name=iPhone 16'
```
