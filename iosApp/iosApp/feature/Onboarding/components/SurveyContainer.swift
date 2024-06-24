//
//  OnboardingStep.swift
//  iosApp
//
//  Created by Shubham Tomar on 24/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

struct SurveyContainer {
    let imageName: String
    let title: String
    let description: String
    let options: [String]
    var selectedOption : String
    let onboardingStep : OnboardingStep
}

  
struct OnboardingDataProvider {
    
  
     static let onboardingSteps: [SurveyContainer] = [
        createOnboardingStep(
            imageName: "immigration-status",
            titleKey: "immigration_status_title",
            descriptionKey: "immigration_status_description",
            optionKeys: ["immigration_status_option_1", "immigration_status_option_2"],
            onboardingStep: .immigrationStatus
        ),
        createOnboardingStep(
            imageName: "pathway",
            titleKey: "pathway_title",
            descriptionKey: "pathway_description",
            optionKeys: [
                "pathway_option_1", "pathway_option_2", "pathway_option_3", "pathway_option_4",
                "pathway_option_5", "pathway_option_6", "pathway_option_7", "pathway_option_8",
                "pathway_option_9", "pathway_option_10"
            ],
            onboardingStep: .pathway
        ),
        createOnboardingStep(
            imageName: "target-province",
            titleKey: "target_province_title",
            descriptionKey: "target_province_description",
            optionKeys: [
                "target_province_option_1", "target_province_option_2", "target_province_option_3",
                "target_province_option_4", "target_province_option_5", "target_province_option_6",
                "target_province_option_7", "target_province_option_8", "target_province_option_9",
                "target_province_option_10", "target_province_option_11", "target_province_option_12",
                "target_province_option_13"
            ],
            onboardingStep: .targetProvince
        ),
        
        createOnboardingStep(
            imageName: "language-proficiency",
            titleKey: "language_proficiency_title",
            descriptionKey: "language_proficiency_description",
            optionKeys: [
                "language_proficiency_option_1", "language_proficiency_option_2",
                "language_proficiency_option_3", "language_proficiency_option_4",
                "language_proficiency_option_5"
            ],
            onboardingStep: .languageProficiency
        ),
        createOnboardingStep(
            imageName: "employment-status",
            titleKey: "employment_status_title",
            descriptionKey: "employment_status_description",
            optionKeys: [
                "employment_status_option_1", "employment_status_option_2",
                "employment_status_option_3", "employment_status_option_4",
                "employment_status_option_5"
            ],
            onboardingStep: .employmentStatus
        ),
        createOnboardingStep(
            imageName: "occupation-industry",
            titleKey: "occupation_industry_title",
            descriptionKey: "occupation_industry_description",
            optionKeys: [
                "occupation_industry_option_1", "occupation_industry_option_2",
                "occupation_industry_option_3", "occupation_industry_option_4",
                "occupation_industry_option_5", "occupation_industry_option_6",
                "occupation_industry_option_7", "occupation_industry_option_8",
                "occupation_industry_option_9", "occupation_industry_option_10",
                "occupation_industry_option_11"
            ],
            onboardingStep: .occupationIndustry
        ),
        createOnboardingStep(
            imageName: "education",
            titleKey: "education_title",
            descriptionKey: "education_description",
            optionKeys: [
                "education_option_1", "education_option_2", "education_option_3",
                "education_option_4", "education_option_5", "education_option_6",
                "education_option_7"
            ],
            onboardingStep: .education
        ),
        createOnboardingStep(
            imageName: "family-status",
            titleKey: "family_status_title",
            descriptionKey: "family_status_description",
            optionKeys: [
                "family_status_option_1", "family_status_option_2"
            ],
            onboardingStep: .familyStatus
        ),
        createOnboardingStep(
            imageName: "housing-preferences",
            titleKey: "housing_preferences_title",
            descriptionKey: "housing_preferences_description",
            optionKeys: [
                "housing_preferences_option_1", "housing_preferences_option_2",
                "housing_preferences_option_3", "housing_preferences_option_4",
                "housing_preferences_option_5"
            ],
            onboardingStep: .housingPreferences
        ),
        createOnboardingStep(
            imageName: "settlement-services",
            titleKey: "settlement_services_title",
            descriptionKey: "settlement_services_description",
            optionKeys: [
                "settlement_services_option_1", "settlement_services_option_2",
                "settlement_services_option_3", "settlement_services_option_4",
                "settlement_services_option_5", "settlement_services_option_6",
                "settlement_services_option_7"
            ],
            onboardingStep: .settlementServices
        ),
        createOnboardingStep(
            imageName: "transportation-needs",
            titleKey: "transportation_needs_title",
            descriptionKey: "transportation_needs_description",
            optionKeys: [
                "transportation_needs_option_1", "transportation_needs_option_2"
            ],
            onboardingStep: .transportationNeeds
        )
    ]
    
 

}


extension OnboardingDataProvider{
    static func createOnboardingStep(imageName: String, titleKey: String, descriptionKey: String, optionKeys: [String], onboardingStep : OnboardingStep) -> SurveyContainer {
        return SurveyContainer(
            imageName: imageName,
            title: NSLocalizedString(titleKey, comment: ""),
            description: NSLocalizedString(descriptionKey, comment: ""),
            options: optionKeys.map { NSLocalizedString($0, comment: "") },
            selectedOption: "",
            onboardingStep: onboardingStep
        )
    }
}
