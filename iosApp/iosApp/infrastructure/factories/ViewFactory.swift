//
//  ViewFactory.swift
//  iosApp
//
//  Created by Shubham Tomar on 20/02/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI

protocol ViewFactory {
    @ViewBuilder
    func build(screen: Screen) -> AnyView
    
    
    @ViewBuilder
    func build(sheet : Sheet) -> AnyView
    
    
    @ViewBuilder
    func build(fullScreenCover : FullScreenCover) -> AnyView
}

class ViewFactoryImp: ViewFactory {
    
    init(viewModelFactory: ViewModelFactory) {
        self.viewModelFactory = viewModelFactory
    }
    
    let viewModelFactory: ViewModelFactory
    
    func build(screen: Screen) -> AnyView {
        switch screen {
            
        case .splashScreen:
            return AnyView(SplashScreen())
        case .dashboardScreen :
            return AnyView(DashboardScreen(viewModel: self.viewModelFactory.makeDashboardViewModel()))
        case .homeScreen:
            return AnyView(HomeScreen(viewModel: self.viewModelFactory.makeHomeViewModel()))
        case .programsScreen:
            return AnyView(ProgramsScreen(viewModel: self.viewModelFactory.makeProgramsViewModel()))
        case .crsScreen:
            return AnyView(CrsScreen(viewModel: self.viewModelFactory.makeCrsViewModel()))
        case .profileScreen:
            return AnyView(ProfileScreen(viewModel: self.viewModelFactory.makeProfileViewModel()))
        case .processScreen:
            return AnyView(ProcessScreen(viewModel: self.viewModelFactory.makeProcessViewModel()))
        case .onboardingScreen:
            return AnyView(OnboardingScreen(viewModel: self.viewModelFactory.makeOnboardingViewModel()))
        case .signup:
            return AnyView(SignupScreen(viewModel: self.viewModelFactory.makeSignupViewModel()))
        case .login:
            return AnyView(LoginScreen(viewModel: self.viewModelFactory.makeLoginViewModel()))
        case .forgetPassword:
            return AnyView(ForgetPasswordScreen(viewModel: self.viewModelFactory.makeForgetPasswordViewModel()))
        case .newsDetail(newsId: let newsId):
            return AnyView(NewsDetailScreen(viewModel: self.viewModelFactory.makeNewsDetailViewModel(newsId: newsId)))
        }
        
    }
    
    func build(sheet: Sheet) -> AnyView {
        switch sheet {
            
        case .blankSheet :
            return AnyView(Text("Hello World"))
        case .discussions(id: let id) :
            return AnyView(DiscussionsView())
        }
    }
    
    func build(fullScreenCover: FullScreenCover) -> AnyView {
        switch fullScreenCover {
            
        case .blankCover:
            return AnyView(EmptyView())
            
        }
    }
    
}



