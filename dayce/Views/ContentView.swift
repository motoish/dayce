import SwiftData
import SwiftUI

struct ContentView: View {
    @Query(sort: \DayEvent.date) private var events: [DayEvent]
    @State private var selectedEventID: UUID?

    var body: some View {
        NavigationSplitView {
            EventListView(selection: $selectedEventID)
                .navigationTitle("dayce")
        } detail: {
            if let selectedEvent = EventSelection.selectedEvent(for: selectedEventID, in: events) {
                EventDetailView(event: selectedEvent, selection: $selectedEventID)
            } else {
                ContentUnavailableView(
                    "No Event Selected",
                    systemImage: "calendar",
                    description: Text("Select an event or add a new one.")
                )
            }
        }
    }
}
