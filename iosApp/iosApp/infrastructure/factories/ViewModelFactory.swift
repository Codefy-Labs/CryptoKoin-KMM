//
//  ViewModelFactory.swift
//  iosApp
//
//  Created by Shubham Tomar on 19/02/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

protocol ViewModelFactory {
   
    func makeDashboardViewModel() -> DashboardViewModel
    func makeHomeViewModel() -> HomeViewModel
    func makeProcessViewModel() -> ProcessViewModel
    func makeProgramsViewModel() -> ProgramsViewModel
    func makeCrsViewModel() -> CrsViewModel
    func makeProfileViewModel () -> ProfileViewModel
    func makeOnboardingViewModel() -> OnboardingViewModel
    func makeLoginViewModel() -> LoginViewModel
    func makeSignupViewModel() -> SignupViewModel
    func makeForgetPasswordViewModel() -> ForgetPasswordViewModel
    func makeNewsDetailViewModel(newsId : String) -> NewsDetailViewModel
    
}
 
class ViewModelFactoryImpl: ViewModelFactory {
    
    let sharedViewModelProvider : SharedViewModelProvider
    let coordinatorFactory : CoordinatorFactory
    
    init(sharedViewModelProvider: SharedViewModelProvider, coordinatorFactory : CoordinatorFactory) {
        self.sharedViewModelProvider = sharedViewModelProvider
        self.coordinatorFactory = coordinatorFactory
    }
    
     
    func makeHomeViewModel() -> HomeViewModel {
        HomeViewModel(coordinator: coordinatorFactory.makeHomeCoordinator(), viewModel: sharedViewModelProvider.getHomeViewModel())
    }
    
    func makeDashboardViewModel() -> DashboardViewModel {
        DashboardViewModel(coordinator: coordinatorFactory.makeDashboardCoordinator(), vmShared: sharedViewModelProvider.getDashboardViewModel())
    }
    
    func makeProcessViewModel() -> ProcessViewModel {
        ProcessViewModel(coordinator: coordinatorFactory.makeProcessCoordinator(), vmShared: sharedViewModelProvider.getProcessViewModel())
    }
    
    func makeProgramsViewModel() -> ProgramsViewModel {
        ProgramsViewModel(coordinator: coordinatorFactory.makeProgramsCoordinator(), vmShared: sharedViewModelProvider.getProgramsViewModel())
    }
    
    func makeCrsViewModel() -> CrsViewModel {
        CrsViewModel(coordinator: coordinatorFactory.makeCrsCoordinator(), vmShared: sharedViewModelProvider.getCrsViewModel())
    }
    
    func makeProfileViewModel() -> ProfileViewModel {
        ProfileViewModel(coordinator: coordinatorFactory.makeProfileCoordinator(), vmShared: sharedViewModelProvider.getProfileViewModel())
    }
     
    func makeOnboardingViewModel() -> OnboardingViewModel {
        OnboardingViewModel(coordinator: coordinatorFactory.makeOnboardingCoordinator(), vmShared: sharedViewModelProvider.getOnboardingViewModel())
    }
    
    func makeLoginViewModel() -> LoginViewModel {
        LoginViewModel(coordinator: coordinatorFactory.makeLoginCoordinator(), vmShared: sharedViewModelProvider.getLoginViewModel())
    }
    
    func makeSignupViewModel() -> SignupViewModel {
        SignupViewModel(coordinator: coordinatorFactory.makeSignupCoordinator(), vmShared: sharedViewModelProvider.getSignupViewModel())
    }
    
    func makeForgetPasswordViewModel() -> ForgetPasswordViewModel {
        ForgetPasswordViewModel(sharedForgetPasswordViewModel: sharedViewModelProvider.getForgetPasswordViewModel(), coordinator: coordinatorFactory.makeForgetPasswordCoordinator())
    }
    
    func makeNewsDetailViewModel(newsId : String) -> NewsDetailViewModel {
        NewsDetailViewModel(coordinator: coordinatorFactory.makeNewsDetailCoordinator(), vmShared: sharedViewModelProvider.getNewsDetailViewModel(newsId: newsId))
    }
}

