import SwiftData
import SwiftUI

@main
struct dayceApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
        .modelContainer(for: DayEvent.self)
    }
}
