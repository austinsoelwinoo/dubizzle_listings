# Dubizzle Listings

Hi! I'm [Soe Lwin Oo](https://www.linkedin.com/in/soelwinoo0110/), Senior Android Developer. This repo is building simple list-detail android application for showing sample Dubizzle listings from static API. Additionally, I have implemented new features in develop branch. Feel free to check out and give feedback.

## Screens

- List Screen => Showing product listings
- Detail Screen => Showing detail of a selected listing

## Languages
- List Screen => Kotlin
- Detail Screen => Java

## Tech

- **master** branch => Clean Architecture, Image Loading (Glide, Custom Image Loader), API (Retrofit with CoroutineCallAdapter), UI Test (JUnit), Integration Test (Mockito), UI Test (Espresso)
- **develop** branch => Clean Architecture, Dependency Injection (Koin), Image Loading (CoinImage, Custom Image Loader), API (Retrofit with CoroutineCallAdapter), UI (Jetpack Compose), UI Test (JUnit), Integration Test (Mockito), UI Test (Espresso, ComposeTest)

## Features

- **master** branch
    - Show product listings with just product image
    - Show product listing detail with name, price, date and image.

- **develop** branch
    - Show product listings with three types of list UI (simple list, detailed list, grid)
    - Support **search by name**,  **group by date**, **sort by date and price**
    - Show product listing detail with name, price, date and image.

## Known issues
- Jetpack compose is still in experimental stage.
- Clean Architecture layers should be separated with different modules instead of just putting all layers under app module.