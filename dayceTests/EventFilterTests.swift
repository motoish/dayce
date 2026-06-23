import XCTest
@testable import dayce

final class EventFilterTests: XCTestCase {
    func testAllFilterReturnsEveryEvent() {
        let events = sampleEvents()

        let result = EventFilter.all.apply(to: events)

        XCTAssertEqual(result.map(\.name), ["A", "B"])
    }

    func testCountUpFilterReturnsOnlyCountUpEvents() {
        let events = sampleEvents()

        let result = EventFilter.countUp.apply(to: events)

        XCTAssertEqual(result.map(\.name), ["A"])
    }

    func testCountdownFilterReturnsOnlyCountdownEvents() {
        let events = sampleEvents()

        let result = EventFilter.countDown.apply(to: events)

        XCTAssertEqual(result.map(\.name), ["B"])
    }

    private func sampleEvents() -> [DayEvent] {
        [
            DayEvent(name: "A", date: .now, kind: .countUp),
            DayEvent(name: "B", date: .now, kind: .countDown)
        ]
    }
}
