import Foundation

enum DayCalculator {
    static func dayValue(
        for eventDate: Date,
        kind: DayEventKind,
        now: Date = .now,
        calendar: Calendar = .current
    ) -> Int {
        let startOfEvent = calendar.startOfDay(for: eventDate)
        let startOfNow = calendar.startOfDay(for: now)
        let days = calendar.dateComponents([.day], from: startOfNow, to: startOfEvent).day ?? 0

        switch kind {
        case .countUp:
            return -days + 1
        case .countDown:
            return days
        }
    }

    static func suffix(for kind: DayEventKind, value: Int) -> String {
        switch kind {
        case .countUp:
            return value == 1 ? "day" : "days"
        case .countDown:
            if value > 0 {
                return value == 1 ? "day left" : "days left"
            }
            if value == 0 {
                return "today"
            }
            return abs(value) == 1 ? "day ago" : "days ago"
        }
    }
}
