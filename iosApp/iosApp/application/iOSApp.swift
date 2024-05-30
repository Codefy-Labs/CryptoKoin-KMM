import SwiftUI
import shared

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
     
	var body: some Scene {
		WindowGroup {
			ContentView()
                .environment(\.font, Font.custom("DMSans-Regular", size: 16))
                .onAppear(perform: {
                    NetworkStatusNotifier.shared.startMonitoring()
                })
		}
	}
}
