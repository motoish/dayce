import SwiftUI

struct EventDraft {
    var name: String
    var date: Date
    var kind: DayEventKind
    var note: String

    init(event: DayEvent? = nil) {
        self.name = event?.name ?? ""
        self.date = event?.date ?? .now
        self.kind = event?.kind ?? .countDown
        self.note = event?.note ?? ""
    }
}

struct EventFormView: View {
    @Environment(\.dismiss) private var dismiss

    let title: String
    let onSave: (EventDraft) -> Void

    @State private var draft: EventDraft

    private var trimmedName: String {
        draft.name.trimmingCharacters(in: .whitespacesAndNewlines)
    }

    init(title: String, event: DayEvent? = nil, onSave: @escaping (EventDraft) -> Void) {
        self.title = title
        self.onSave = onSave
        _draft = State(initialValue: EventDraft(event: event))
    }

    var body: some View {
        NavigationStack {
            Form {
                Section {
                    TextField("Name", text: $draft.name)
                    DatePicker("Date", selection: $draft.date, displayedComponents: .date)
                    Picker("Type", selection: $draft.kind) {
                        ForEach(DayEventKind.allCases) { kind in
                            Text(kind.displayName).tag(kind)
                        }
                    }
                    .pickerStyle(.segmented)
                }

                Section("Note") {
                    TextField("Optional note", text: $draft.note, axis: .vertical)
                        .lineLimit(3...6)
                }
            }
            .navigationTitle(title)
            .toolbar {
                ToolbarItem(placement: .cancellationAction) {
                    Button("Cancel") {
                        dismiss()
                    }
                }

                ToolbarItem(placement: .confirmationAction) {
                    Button("Save") {
                        var savedDraft = draft
                        savedDraft.name = trimmedName
                        savedDraft.note = savedDraft.note.trimmingCharacters(in: .whitespacesAndNewlines)
                        onSave(savedDraft)
                        dismiss()
                    }
                    .disabled(trimmedName.isEmpty)
                }
            }
        }
    }
}
