# WalletDashboard

A modern Android wallet dashboard application built with traditional Android Views and Material Design.

## Features

- **Modern UI**: Built with Material Design components for a native Android experience
- **MVVM Architecture**: Clean separation of concerns with ViewModel pattern
- **Reactive Programming**: State management with Kotlin coroutines
- **Network Layer**: RESTful API integration with Retrofit
- **Image Loading**: Efficient image loading with Glide

## Tech Stack

- **Language**: Kotlin 1.8.10
- **UI Framework**: Traditional Android Views with Material Design
- **Architecture**: MVVM with ViewModel
- **Minimum SDK**: API 21 (Android 5.0)
- **Target SDK**: API 34 (Android 14)
- **Java Version**: Java 11

## Project Structure

```
app/src/main/java/com/rainfool/wallet/
├── MainActivity.kt              # Main activity
├── ui/
│   ├── MainViewModel.kt         # Main view model
│   └── WalletBalanceAdapter.kt  # RecyclerView adapter
├── data/
│   ├── model/                   # Data models
│   ├── network/                 # API layer
│   └── repository/              # Data repository
└── di/
    └── DependencyProvider.kt    # Dependency injection
```

## Getting Started

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd WalletDashboard
   ```

2. **Build and run**
   ```bash
   ./gradlew build
   ./gradlew installDebug
   ```

## Dependencies

- **AndroidX Core KTX**: 1.10.1
- **AndroidX Lifecycle**: 2.5.1
- **Material Design**: 1.9.0
- **Retrofit**: 2.9.0 (Network requests)
- **Coroutines**: 1.6.4 (Async programming)
- **Glide**: 4.15.1 (Image loading)
- **Gson**: 2.10.1 (JSON parsing)

## Requirements

- Android Studio Arctic Fox or later
- JDK 11 or higher
- Android SDK API 21+

## License

This project is licensed under the MIT License. 