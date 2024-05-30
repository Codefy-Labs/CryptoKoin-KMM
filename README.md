# CryptoKoin-KMM

Welcome to the CryptoKoin-KMM repository! This project is a Kotlin Multiplatform Mobile (KMM) application that provides real-time cryptocurrency data using the GeckoCoin API. The application is designed to offer users a seamless experience on both Android and iOS platforms.

## Overview

CryptoKoin is a cross-platform mobile application that allows users to:

- View the latest cryptocurrency prices.
- Track historical data and trends.
- Stay updated with real-time market changes.

This project leverages the power of KMM to share common code between Android and iOS applications, reducing development time and ensuring consistency across platforms.

## Features

- **Real-time Data**: Fetches up-to-date cryptocurrency prices and market data using the GeckoCoin API.
- **Cross-Platform**: Built using Kotlin Multiplatform Mobile, enabling code sharing between Android and iOS.
- **User-Friendly Interface**: Provides a clean and intuitive user interface for easy navigation and data visualization.

## Technologies Used

- **Kotlin Multiplatform Mobile (KMM)**: To share code between Android and iOS applications.
- **GeckoCoin API**: For fetching cryptocurrency data.
- **Ktor**: A framework for making network requests.
- **SQLDelight**: For database management and queries.
- **Kotlin Coroutines**: For asynchronous programming.
- **Jetpack Compose (Android)**: For building the user interface on Android.
- **SwiftUI (iOS)**: For building the user interface on iOS.

## Project Structure

The project is structured as follows:

- `shared/`: Contains the shared Kotlin code for both platforms.
  - `src/commonMain/kotlin/`: Common Kotlin code that is shared across platforms.
  - `src/androidMain/kotlin/`: Android-specific code.
  - `src/iosMain/kotlin/`: iOS-specific code.
- `androidApp/`: Android application module.
- `iosApp/`: iOS application module.

### Key Files and Directories

- `shared/src/commonMain/kotlin/com/cryptokoin/api/`: Contains API service classes for fetching data from the GeckoCoin API.
- `shared/src/commonMain/kotlin/com/cryptokoin/model/`: Contains data models representing cryptocurrency data.
- `shared/src/commonMain/kotlin/com/cryptokoin/repository/`: Contains repository classes for data operations and business logic.
- `shared/src/commonMain/kotlin/com/cryptokoin/utils/`: Utility classes and functions.

## Screenshots

### iOS

| Home Screen | Search Screen |
|-------------|---------------|
| ![CryptoKoin-IOS-Home](https://github.com/Codefy-Labs/CryptoKoin-KMM/assets/171213082/f45cbbe5-55c8-4795-b7e9-f800e8f8261c) | ![CryptoKoin-IOS-Search](https://github.com/Codefy-Labs/CryptoKoin-KMM/assets/171213082/08b2feee-9f04-4deb-a9ae-3012079bddf3) |

### Android

| Home Screen | Search Screen |
|-------------|---------------|
| ![CryptoKoin-Android-Home](https://github.com/Codefy-Labs/CryptoKoin-KMM/assets/171213082/c1464d09-5c94-4a2a-90c9-23a63cea24c4) | ![CryptoKoin-Android-Search](https://github.com/Codefy-Labs/CryptoKoin-KMM/assets/171213082/212540da-4cb1-4f10-b2da-a26254e25716) |

## Getting Started

### Prerequisites

- Kotlin 1.5+
- Android Studio Arctic Fox or later
- Xcode 12.5 or later
 
