import SwiftData
import SwiftUI

struct EventDetailView: View {
    @Environment(\.modelContext) private var modelContext
    @State private var isEditing = false

    let event: DayEvent

    var body: some View {
        let value = DayCalculator.dayValue(for: event.date, kind: event.kind)

        List {
            Section {
                VStack(alignment: .leading, spacing: 8) {
                    Text("\(abs(value))")
                        .font(.system(size: 64, weight: .bold, design: .rounded))
                        .monospacedDigit()

                    Text(DayCalculator.suffix(for: event.kind, value: value))
                        .font(.title3)
                        .foregroundStyle(.secondary)
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.vertical, 12)
            }

            Section("Details") {
                LabeledContent("Type", value: event.kind.displayName)
                LabeledContent("Date") {
                    Text(event.date, style: .date)
                }
            }

            if !event.note.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty {
                Section("Note") {
                    Text(event.note)
                }
            }

            Section {
                Button("Delete Event", role: .destructive) {
                    modelContext.delete(event)
                }
            }
        }
        .navigationTitle(event.name)
        .toolbar {
            ToolbarItem(placement: .primaryAction) {
                Button("Edit") {
                    isEditing = true
                }
            }
        }
        .sheet(isPresented: $isEditing) {
            EventFormView(title: "Edit Event", event: event) { draft in
                event.name = draft.name
                event.date = draft.date
                event.kind = draft.kind
                event.note = draft.note
                event.updatedAt = .now
            }
        }
    }
}
