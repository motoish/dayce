import SwiftData
import SwiftUI

struct ContentView: View {
    @State private var selectedEvent: DayEvent?

    var body: some View {
        NavigationSplitView {
            EventListView(selection: $selectedEvent)
                .navigationTitle("dayce")
        } detail: {
            if let selectedEvent {
                EventDetailView(event: selectedEvent)
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
