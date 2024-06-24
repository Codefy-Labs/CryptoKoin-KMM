package com.codefylabs.www.canimmigrate.android.ui.onboarding.components

import com.codefylabs.www.canimmigrate.android.R
import com.codefylabs.www.canimmigrate.dashboard.presentation.onboarding.OnboardingStep

data class SurveyContainer(
    val onboardingStep: OnboardingStep,
    val imageName: Int,
    val title: String,
    val description: String,
    val options: List<String>,
    var selectedOption: String = "",
)

class OnboardingDataProvider(val getStringResourceById: (Int) -> String) {

    private fun createSurveyContainer(
        onboardingStep: OnboardingStep,
        imageName: Int,
        titleKey: Int,
        descriptionKey: Int,
        optionKeys: List<Int>,
    ): SurveyContainer {
        return SurveyContainer(
            imageName = imageName,
            title = getStringResourceByName(titleKey),
            description = getStringResourceByName(descriptionKey),
            options = optionKeys.map { getStringResourceByName(it) },
            onboardingStep = onboardingStep
        )
    }

    private fun getStringResourceByName(resId: Int): String {
        return getStringResourceById(resId)
    }

    val surveyContainers: List<SurveyContainer> = listOf(
        createSurveyContainer(
            imageName = R.drawable.ob_immigration_status,
            titleKey = R.string.immigration_status_title,
            descriptionKey = R.string.immigration_status_description,
            optionKeys = listOf(
                R.string.immigration_status_option_1,
                R.string.immigration_status_option_2
            ),
            onboardingStep = OnboardingStep.IMMIGRATION_STATUS
        ),
        createSurveyContainer(
            imageName = R.drawable.ob_pathway,
            titleKey = R.string.pathway_title,
            descriptionKey = R.string.pathway_description,
            optionKeys = listOf(
                R.string.pathway_option_1,
                R.string.pathway_option_2,
                R.string.pathway_option_3,
                R.string.pathway_option_4,
                R.string.pathway_option_5,
                R.string.pathway_option_6,
                R.string.pathway_option_7,
                R.string.pathway_option_8,
                R.string.pathway_option_9,
                R.string.pathway_option_10
            ),
            onboardingStep = OnboardingStep.PATHWAY
        ),
        createSurveyContainer(
            imageName = R.drawable.ob_target_province,
            titleKey = R.string.target_province_title,
            descriptionKey = R.string.target_province_description,
            optionKeys = listOf(
                R.string.target_province_option_1,
                R.string.target_province_option_2,
                R.string.target_province_option_3,
                R.string.target_province_option_4,
                R.string.target_province_option_5,
                R.string.target_province_option_6,
                R.string.target_province_option_7,
                R.string.target_province_option_8,
                R.string.target_province_option_9,
                R.string.target_province_option_10,
                R.string.target_province_option_11,
                R.string.target_province_option_12,
                R.string.target_province_option_13
            ),
            onboardingStep = OnboardingStep.TARGET_PROVINCE
        ),
        createSurveyContainer(
            imageName = R.drawable.ob_language_proficiency,
            titleKey = R.string.language_proficiency_title,
            descriptionKey = R.string.language_proficiency_description,
            optionKeys = listOf(
                R.string.language_proficiency_option_1, R.string.language_proficiency_option_2,
                R.string.language_proficiency_option_3, R.string.language_proficiency_option_4,
                R.string.language_proficiency_option_5
            ),
            onboardingStep = OnboardingStep.LANGUAGE_PROFICIENCY
        ),
        createSurveyContainer(
            imageName = R.drawable.ob_employment_status,
            titleKey = R.string.employment_status_title,
            descriptionKey = R.string.employment_status_description,
            optionKeys = listOf(
                R.string.employment_status_option_1, R.string.employment_status_option_2,
                R.string.employment_status_option_3, R.string.employment_status_option_4,
                R.string.employment_status_option_5
            ),
            onboardingStep = OnboardingStep.EMPLOYMENT_STATUS


        ),
        createSurveyContainer(
            imageName = R.drawable.ob_occupation_industry,
            titleKey = R.string.occupation_industry_title,
            descriptionKey = R.string.occupation_industry_description,
            optionKeys = listOf(
                R.string.occupation_industry_option_1, R.string.occupation_industry_option_2,
                R.string.occupation_industry_option_3, R.string.occupation_industry_option_4,
                R.string.occupation_industry_option_5, R.string.occupation_industry_option_6,
                R.string.occupation_industry_option_7, R.string.occupation_industry_option_8,
                R.string.occupation_industry_option_9, R.string.occupation_industry_option_10,
                R.string.occupation_industry_option_11
            ),
            onboardingStep = OnboardingStep.OCCUPATION_INDUSTRY
        ),
        createSurveyContainer(
            imageName = R.drawable.ob_education,
            titleKey = R.string.education_title,
            descriptionKey = R.string.education_description,
            optionKeys = listOf(
                R.string.education_option_1,
                R.string.education_option_2,
                R.string.education_option_3,
                R.string.education_option_4,
                R.string.education_option_5,
                R.string.education_option_6,
                R.string.education_option_7
            ),
            onboardingStep = OnboardingStep.EDUCATION
        ),
        createSurveyContainer(
            imageName = R.drawable.ob_family_status,
            titleKey = R.string.family_status_title,
            descriptionKey = R.string.family_status_description,
            optionKeys = listOf(
                R.string.family_status_option_1, R.string.family_status_option_2
            ),
            onboardingStep = OnboardingStep.FAMILY_STATUS
        ),
        createSurveyContainer(
            imageName = R.drawable.ob_housing_preferences,
            titleKey = R.string.housing_preferences_title,
            descriptionKey = R.string.housing_preferences_description,
            optionKeys = listOf(
                R.string.housing_preferences_option_1, R.string.housing_preferences_option_2,
                R.string.housing_preferences_option_3, R.string.housing_preferences_option_4,
                R.string.housing_preferences_option_5
            ),
            onboardingStep = OnboardingStep.HOUSING_PREFERENCES
        ),
        createSurveyContainer(
            imageName = R.drawable.ob_settlement_services,
            titleKey = R.string.settlement_services_title,
            descriptionKey = R.string.settlement_services_description,
            optionKeys = listOf(
                R.string.settlement_services_option_1, R.string.settlement_services_option_2,
                R.string.settlement_services_option_3, R.string.settlement_services_option_4,
                R.string.settlement_services_option_5, R.string.settlement_services_option_6,
                R.string.settlement_services_option_7
            ),
            onboardingStep = OnboardingStep.SETTLEMENT_SERVICES
        ),
        createSurveyContainer(
            imageName = R.drawable.ob_transportation_needs,
            titleKey = R.string.transportation_needs_title,
            descriptionKey = R.string.transportation_needs_description,
            optionKeys = listOf(
                R.string.transportation_needs_option_1, R.string.transportation_needs_option_2
            ),
            onboardingStep = OnboardingStep.TRANSPORTATION_NEEDS
        )
    )


}
