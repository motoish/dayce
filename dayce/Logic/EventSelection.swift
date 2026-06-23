import Foundation

enum EventSelection {
    static func selectedEvent(for selectedID: UUID?, in events: [DayEvent]) -> DayEvent? {
        guard let selectedID else {
            return nil
        }

        return events.first { $0.id == selectedID }
    }

    static func clearSelectionIfNeeded(deletedEventID: UUID, selection: inout UUID?) {
        if selection == deletedEventID {
            selection = nil
        }
    }
}
