import Foundation
import SwiftData

enum DayEventKind: String, Codable, CaseIterable, Identifiable {
    case countUp
    case countDown

    var id: String { rawValue }

    var displayName: String {
        switch self {
        case .countUp:
            return "Count Up"
        case .countDown:
            return "Countdown"
        }
    }
}

@Model
final class DayEvent {
    @Attribute(.unique) var id: UUID
    var name: String
    var date: Date
    var kindRawValue: String
    var note: String
    var createdAt: Date
    var updatedAt: Date

    var kind: DayEventKind {
        get { DayEventKind(rawValue: kindRawValue) ?? .countDown }
        set { kindRawValue = newValue.rawValue }
    }

    init(
        id: UUID = UUID(),
        name: String,
        date: Date,
        kind: DayEventKind,
        note: String = "",
        createdAt: Date = .now,
        updatedAt: Date = .now
    ) {
        self.id = id
        self.name = name
        self.date = date
        self.kindRawValue = kind.rawValue
        self.note = note
        self.createdAt = createdAt
        self.updatedAt = updatedAt
    }
}
