import XCTest
@testable import dayce

final class EventSelectionTests: XCTestCase {
    func testSelectedEventReturnsMatchingID() {
        let selected = DayEvent(name: "Selected", date: .now, kind: .countDown)
        let other = DayEvent(name: "Other", date: .now, kind: .countUp)

        let result = EventSelection.selectedEvent(for: selected.id, in: [other, selected])

        XCTAssertEqual(result?.id, selected.id)
    }

    func testSelectionClearsWhenDeletedEventIsSelected() {
        let event = DayEvent(name: "Selected", date: .now, kind: .countDown)
        var selectedID: UUID? = event.id

        EventSelection.clearSelectionIfNeeded(deletedEventID: event.id, selection: &selectedID)

        XCTAssertNil(selectedID)
    }

    func testSelectionStaysWhenDeletingAnotherEvent() {
        let selected = DayEvent(name: "Selected", date: .now, kind: .countDown)
        let deleted = DayEvent(name: "Deleted", date: .now, kind: .countUp)
        var selectedID: UUID? = selected.id

        EventSelection.clearSelectionIfNeeded(deletedEventID: deleted.id, selection: &selectedID)

        XCTAssertEqual(selectedID, selected.id)
    }
}
