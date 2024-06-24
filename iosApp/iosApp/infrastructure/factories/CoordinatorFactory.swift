//
//  CoordinatorFactory.swift
//  iosApp
//
//  Created by Shubham Tomar on 19/02/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
 


protocol CoordinatorFactory {
    var navigator: Navigator { get }
     
    
    func makeDashboardCoordinator() -> DashboardCoordinator
    func makeHomeCoordinator() -> HomeCoordinator
    func makeCrsCoordinator() -> CsrCoordinator
    func makeProgramsCoordinator() -> ProgramsCoordinator
    func makeProcessCoordinator() -> ProcessCoordinator
    func makeProfileCoordinator() -> ProfileCoordinator
    func makeOnboardingCoordinator() -> OnboardingCoordinator
    func makeSignupCoordinator() -> SignupCoordinator
    func makeLoginCoordinator() -> LoginCoordinator
}
 

class CoordinatorFactoryImpl: CoordinatorFactory {
 
  
    let navigator: Navigator
    
    init(navigator: Navigator) {
        self.navigator = navigator
    }
    
    func makeDashboardCoordinator() ->  DashboardCoordinator {
        DashboardCoordinatorImpl(navigator: navigator)
    }
      
    func makeHomeCoordinator() -> HomeCoordinator {
        HomeCoordinatorImpl  (navigator: navigator)
    }
    
    func makeCrsCoordinator() ->   CsrCoordinator {
        CsrCoordinatorImpl(navigator: navigator)
    }
    
    func makeProgramsCoordinator() ->   ProgramsCoordinator {
        ProgramsCoordinatorImpl(navigator: navigator)
    }
    
    func makeProcessCoordinator() ->   ProcessCoordinator {
        ProcessCoordinatorImpl(navigator: navigator)
    }
    
    func makeProfileCoordinator() ->   ProfileCoordinator {
        ProfileCoordinatorImpl(navigator: navigator)
    }
     
    func makeOnboardingCoordinator() -> OnboardingCoordinator {
        OnboardingCoordinatorImpl(navigator: navigator)
    }
    
    func makeSignupCoordinator() -> any SignupCoordinator {
        SignupCoordinatorImpl(navigator: navigator)
    }
    
    func makeLoginCoordinator() -> any LoginCoordinator {
        LoginCoordinatorImpl(navigator: navigator)
    }
    
}
