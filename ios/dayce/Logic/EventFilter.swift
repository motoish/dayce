import Foundation

enum EventFilter: String, CaseIterable, Identifiable {
    case all
    case countUp
    case countDown

    var id: String { rawValue }

    var displayName: String {
        switch self {
        case .all:
            return "All"
        case .countUp:
            return "Count Up"
        case .countDown:
            return "Countdown"
        }
    }

    func apply(to events: [DayEvent]) -> [DayEvent] {
        switch self {
        case .all:
            return events
        case .countUp:
            return events.filter { $0.kind == .countUp }
        case .countDown:
            return events.filter { $0.kind == .countDown }
        }
    }
}
