Android Books App

Overview
 - This Android project is developed using Kotlin with Jetpack Compose and follows the MVVM pattern within the Clean Architecture framework. Dependency injection is managed using Hilt, and the project structure includes repository-use case-view model layers.

Features
 - Kotlin and Jetpack Compose: The app is written entirely in Kotlin using Jetpack Compose for modern, declarative UI development.
 - MVVM Pattern: The project follows the MVVM pattern to separate concerns and manage the UI-related data lifecycle.
 - Clean Architecture: The app is structured using the Clean Architecture approach to ensure a scalable and maintainable codebase.
 - Dependency Injection: Hilt is used for dependency injection, facilitating better management of dependencies and easier testing.

Screens
 - Splash Screen: The app starts with a splash screen.
 - Main Screen: The second screen features a horizontal slider with banners and a categorized list of books by genres.
 - Book Details Screen: On the third screen, users can view book details with horizontal swiping to switch between books. It also includes a list of recommended books at the bottom.

Build Configuration
 - The project uses a libs.version.toml file for managing Gradle dependencies and configurations.
