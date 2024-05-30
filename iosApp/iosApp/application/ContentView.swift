import SwiftUI
import shared

struct ContentView: View {
    @Environment(\.scenePhase) var scenePhase
    @StateObject private var toastManager = ToastManager.shared
    @StateObject private var appViewModel = AppIOSViewModel()
    
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
                withAnimation{
                    navigator.replaceRootView(.homeScreen)
                }
            })
            .toastView(toast: $toastManager.toast)
            .task {
                appViewModel.observe()
            }
//            .preferredColorScheme(appViewModel.state.isDarkMode ? .dark : appViewModel.state.isDarkMode  == false ? .light : nil)
         
        
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
