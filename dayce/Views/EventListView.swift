import SwiftData
import SwiftUI

struct EventListView: View {
    @Environment(\.modelContext) private var modelContext
    @Query(sort: \DayEvent.date) private var events: [DayEvent]

    @Binding var selection: DayEvent?
    @State private var filter = EventFilter.all
    @State private var isAdding = false

    private var filteredEvents: [DayEvent] {
        filter.apply(to: events)
    }

    var body: some View {
        Group {
            if events.isEmpty {
                ContentUnavailableView {
                    Label("No Events", systemImage: "calendar.badge.plus")
                } description: {
                    Text("Add your first count-up or countdown day.")
                } actions: {
                    Button("Add Event") {
                        isAdding = true
                    }
                }
            } else {
                List {
                    Picker("Filter", selection: $filter) {
                        ForEach(EventFilter.allCases) { filter in
                            Text(filter.displayName).tag(filter)
                        }
                    }
                    .pickerStyle(.segmented)
                    .listRowSeparator(.hidden)

                    ForEach(filteredEvents) { event in
                        Button {
                            selection = event
                        } label: {
                            EventRowView(event: event)
                                .contentShape(Rectangle())
                        }
                        .buttonStyle(.plain)
                    }
                    .onDelete(perform: deleteEvents)
                }
            }
        }
        .toolbar {
            ToolbarItem(placement: .primaryAction) {
                Button {
                    isAdding = true
                } label: {
                    Label("Add Event", systemImage: "plus")
                }
            }
        }
        .sheet(isPresented: $isAdding) {
            EventFormView(title: "New Event") { draft in
                let event = DayEvent(
                    name: draft.name,
                    date: draft.date,
                    kind: draft.kind,
                    note: draft.note
                )
                modelContext.insert(event)
                selection = event
            }
        }
    }

    private func deleteEvents(at offsets: IndexSet) {
        for index in offsets {
            let event = filteredEvents[index]
            if selection?.id == event.id {
                selection = nil
            }
            modelContext.delete(event)
        }
    }
}
