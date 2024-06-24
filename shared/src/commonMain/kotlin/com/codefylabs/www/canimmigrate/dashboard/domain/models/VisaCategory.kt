package com.codefylabs.www.canimmigrate.dashboard.domain.models

data class VisaCategory(
    val title: String,
    val description: String,
) {
    companion object {
        val visaCategories = listOf(
            VisaCategory("Student Visa", "A Brief overview of the process"),
            VisaCategory("Tourist Visa", "A Brief overview of the process"),
            VisaCategory("Work Permit Visa", "A Brief overview of the process"),
            VisaCategory("Business Visa", "A Brief overview of the process"),
            VisaCategory("Family Sponsorship", "A Brief overview of the process")
        )
    }
}