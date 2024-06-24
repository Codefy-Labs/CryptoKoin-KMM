package com.codefylabs.www.canimmigrate.auth.domain.models


data class Country(
    val code: String,
    val name: String,
    val states: List<CountryState>,
    val flagUrl: String = "https://flagcdn.com/32x24/${code}.png",
)

data class CountryState(
    val name: String,
    val code: String
)

fun generateCountries() = listOf(
    Country(
        code = "ca",
        name = "Canada",
        states = listOf(
            CountryState("Alberta", "AB"),
            CountryState("British Columbia", "BC"),
            CountryState("Manitoba", "MB"),
            CountryState("New Brunswick", "NB"),
            CountryState("Newfoundland and Labrador", "NL"),
            CountryState("Nova Scotia", "NS"),
            CountryState("Ontario", "ON"),
            CountryState("Prince Edward Island", "PE"),
            CountryState("Quebec", "QC"),
            CountryState("Saskatchewan", "SK"),
            CountryState("Northwest Territories", "NT"),
            CountryState("Nunavut", "NU"),
            CountryState("Yukon", "YT")
        )
    ),
    Country(
        code = "us",
        name = "United States",
        states = listOf(
            CountryState("Alabama", "AL"),
            CountryState("Alaska", "AK"),
            CountryState("Arizona", "AZ"),
            CountryState("Arkansas", "AR"),
            CountryState("California", "CA"),
            CountryState("Colorado", "CO"),
            CountryState("Connecticut", "CT"),
            CountryState("Delaware", "DE"),
            CountryState("Florida", "FL"),
            CountryState("Georgia", "GA"),
            CountryState("Hawaii", "HI"),
            CountryState("Idaho", "ID"),
            CountryState("Illinois", "IL"),
            CountryState("Indiana", "IN"),
            CountryState("Iowa", "IA"),
            CountryState("Kansas", "KS"),
            CountryState("Kentucky", "KY"),
            CountryState("Louisiana", "LA"),
            CountryState("Maine", "ME"),
            CountryState("Maryland", "MD"),
            CountryState("Massachusetts", "MA"),
            CountryState("Michigan", "MI"),
            CountryState("Minnesota", "MN"),
            CountryState("Mississippi", "MS"),
            CountryState("Missouri", "MO"),
            CountryState("Montana", "MT"),
            CountryState("Nebraska", "NE"),
            CountryState("Nevada", "NV"),
            CountryState("New Hampshire", "NH"),
            CountryState("New Jersey", "NJ"),
            CountryState("New Mexico", "NM"),
            CountryState("New York", "NY"),
            CountryState("North Carolina", "NC"),
            CountryState("North Dakota", "ND"),
            CountryState("Ohio", "OH"),
            CountryState("Oklahoma", "OK"),
            CountryState("Oregon", "OR"),
            CountryState("Pennsylvania", "PA"),
            CountryState("Rhode Island", "RI"),
            CountryState("South Carolina", "SC"),
            CountryState("South Dakota", "SD"),
            CountryState("Tennessee", "TN"),
            CountryState("Texas", "TX"),
            CountryState("Utah", "UT"),
            CountryState("Vermont", "VT"),
            CountryState("Virginia", "VA"),
            CountryState("Washington", "WA"),
            CountryState("West Virginia", "WV"),
            CountryState("Wisconsin", "WI"),
            CountryState("Wyoming", "WY")
        )
    )
)

