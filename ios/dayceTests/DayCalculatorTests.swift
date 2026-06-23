import XCTest
@testable import dayce

final class DayCalculatorTests: XCTestCase {
    private let calendar = Calendar(identifier: .gregorian)

    func testCountUpSameDayReturnsOne() {
        let start = calendar.date(from: DateComponents(year: 2026, month: 6, day: 23))!
        let now = calendar.date(from: DateComponents(year: 2026, month: 6, day: 23, hour: 23))!

        let result = DayCalculator.dayValue(for: start, kind: .countUp, now: now, calendar: calendar)

        XCTAssertEqual(result, 1)
    }

    func testCountUpNextDayReturnsTwo() {
        let start = calendar.date(from: DateComponents(year: 2026, month: 6, day: 23))!
        let now = calendar.date(from: DateComponents(year: 2026, month: 6, day: 24))!

        let result = DayCalculator.dayValue(for: start, kind: .countUp, now: now, calendar: calendar)

        XCTAssertEqual(result, 2)
    }

    func testCountdownTargetDayReturnsZero() {
        let target = calendar.date(from: DateComponents(year: 2026, month: 6, day: 23))!
        let now = calendar.date(from: DateComponents(year: 2026, month: 6, day: 23, hour: 10))!

        let result = DayCalculator.dayValue(for: target, kind: .countDown, now: now, calendar: calendar)

        XCTAssertEqual(result, 0)
    }

    func testCountdownFutureDayReturnsPositiveRemainingDays() {
        let target = calendar.date(from: DateComponents(year: 2026, month: 6, day: 30))!
        let now = calendar.date(from: DateComponents(year: 2026, month: 6, day: 23))!

        let result = DayCalculator.dayValue(for: target, kind: .countDown, now: now, calendar: calendar)

        XCTAssertEqual(result, 7)
    }

    func testCountdownPastDayReturnsNegativeElapsedDays() {
        let target = calendar.date(from: DateComponents(year: 2026, month: 6, day: 20))!
        let now = calendar.date(from: DateComponents(year: 2026, month: 6, day: 23))!

        let result = DayCalculator.dayValue(for: target, kind: .countDown, now: now, calendar: calendar)

        XCTAssertEqual(result, -3)
    }
}
