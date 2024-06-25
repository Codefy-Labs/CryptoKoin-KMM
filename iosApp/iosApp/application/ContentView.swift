import SwiftUI
import GoogleSignIn
import shared

struct ContentView: View {
    @Environment(\.scenePhase) var scenePhase
    @StateObject private var toastManager = ToastManager.shared
    
    @StateObject private var navigator = Factories.current.navigator
    private let viewFactory = Factories.current.viewFactory
    
    var body: some View {
        VStack(spacing : 0){
            
            NavigationStack(path : $navigator.rootPath){
                viewFactory.build(screen: navigator.rootView)
                    .navigationDestination(for: Screen.self, destination: { screen in
                        viewFactory.build(screen: screen)
                    })
                    .sheet(item: $navigator.sheet, onDismiss: {
                        navigator.dismissSheet()
                    }, content: { sheet in
                        viewFactory.build(sheet: sheet)
                    })
                    .fullScreenCover(item: $navigator.fullScreenCover, content: { fullScreenCover in
                        viewFactory.build(fullScreenCover: fullScreenCover)
                    })
                
            }
        }.navigationBarHidden(true) // Hide the default navigation bar
            .onAppear(perform: {
                DispatchQueue.main.asyncAfter(deadline: .now() + 2.0) {
                    withAnimation{
                        navigator.replaceRootView(.dashboardScreen)
                    }
                }
            })
            .accentColor(.red)
            .toastView(toast: $toastManager.toast)
        
    }
}


