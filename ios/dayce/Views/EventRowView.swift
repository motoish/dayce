import SwiftUI

struct EventRowView: View {
    let event: DayEvent
    var now: Date = .now

    var body: some View {
        let value = DayCalculator.dayValue(for: event.date, kind: event.kind, now: now)

        HStack(spacing: 12) {
            VStack(alignment: .leading, spacing: 4) {
                Text(event.name)
                    .font(.headline)
                    .lineLimit(1)

                Text(event.date, style: .date)
                    .font(.subheadline)
                    .foregroundStyle(.secondary)

                if !event.note.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty {
                    Text(event.note)
                        .font(.caption)
                        .foregroundStyle(.secondary)
                        .lineLimit(1)
                }
            }

            Spacer()

            VStack(alignment: .trailing, spacing: 2) {
                Text("\(abs(value))")
                    .font(.system(.title2, design: .rounded, weight: .bold))
                    .monospacedDigit()

                Text(DayCalculator.suffix(for: event.kind, value: value))
                    .font(.caption)
                    .foregroundStyle(.secondary)
                    .multilineTextAlignment(.trailing)
            }
        }
        .padding(.vertical, 4)
    }
}
