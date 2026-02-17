# 🛒 Shop Magazine

![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-purple?style=flat&logo=kotlin)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-v1.5-green?style=flat&logo=jetpackcompose)
![Hilt](https://img.shields.io/badge/DI-Hilt-yellow)
![Room](https://img.shields.io/badge/Database-Room-blue)
![Architecture](https://img.shields.io/badge/Architecture-Clean%20%2B%20MVVM-blue)

**Shop Magazine** is a full-featured e-commerce Android application demonstrating a robust **Offline-first** approach and modern state management. The app features a dynamic product catalog, advanced filtering/sorting, a reactive shopping cart, and secure user authentication via DataStore and Interceptors.

## 🚀 Key Features

* **Dynamic Catalog:** Fetches high-quality product data from the FakeStore API.
* **Advanced Filtering & Sorting:** Users can filter products by category, rating, and search queries, or sort by price, alphabet, and popularity.
* **Reactive Shopping Cart:** A fully functional cart with the ability to add, increment, decrement, and remove items with real-time total price calculation.
* **Offline Support (Single Source of Truth):** Uses Room Database to cache products and cart items, ensuring the app remains functional without an internet connection.
* **Secure Auth & Session Management:**
    * JWT Token storage using **Jetpack DataStore**.
    * **AuthInterceptor** for automatic header injection in API requests.
* **Unit & UI Testing:** Comprehensive test suite for DAOs, Repositories, and ViewModels using MockK and Google Truth.

## 🏗 Architecture & Patterns

The project follows **Clean Architecture** principles combined with **MVI/MVVM** patterns:

1.  **Presentation Layer:** Built with **Jetpack Compose**. Uses `StateFlow` and `combine` operators to merge multiple data streams (search, filters, database) into a single UI State.
2.  **Domain Layer:** Defines the business logic and repository interfaces, ensuring the app is independent of external frameworks.
3.  **Data Layer:** Handles data coordination between the **Retrofit** REST API and **Room** local storage.



## 🛠 Tech Stack

* **UI:** Jetpack Compose (Material 3).
* **Dependency Injection:** Dagger Hilt.
* **Database:** Room (with Foreign Keys and Transactions).
* **Networking:** Retrofit 2 & GSON.
* **Preferences:** Jetpack DataStore.
* **Concurrency:** Kotlin Coroutines & Flow (Debounce, Combine, stateIn).
* **Testing:** MockK, JUnit4, Google Truth, Robolectric.

## 📸 Screenshots

| Product Catalog | Product Details | Filters & Sort | Shopping Cart |
|:---:|:---:|:---:|:---:|
| <img src="https://private-user-images.githubusercontent.com/200123932/551058903-e7a67e89-3013-479a-b946-0f012b349666.jpeg?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NzEzNDQxOTIsIm5iZiI6MTc3MTM0Mzg5MiwicGF0aCI6Ii8yMDAxMjM5MzIvNTUxMDU4OTAzLWU3YTY3ZTg5LTMwMTMtNDc5YS1iOTQ2LTBmMDEyYjM0OTY2Ni5qcGVnP1gtQW16LUFsZ29yaXRobT1BV1M0LUhNQUMtU0hBMjU2JlgtQW16LUNyZWRlbnRpYWw9QUtJQVZDT0RZTFNBNTNQUUs0WkElMkYyMDI2MDIxNyUyRnVzLWVhc3QtMSUyRnMzJTJGYXdzNF9yZXF1ZXN0JlgtQW16LURhdGU9MjAyNjAyMTdUMTU1ODEyWiZYLUFtei1FeHBpcmVzPTMwMCZYLUFtei1TaWduYXR1cmU9ZDhkYTIzZGY0MmIwNTc4Njk3NTZkOWEzMTJmYjMxYTE5YTEzNjkwOGZhODBlYWY5OTk3ZjYwZWVhNjAzZjliZCZYLUFtei1TaWduZWRIZWFkZXJzPWhvc3QifQ.XytN0JnBC4xGfPLvdWsZ_qihRGRh1hIEdNdcTWVH3Zs" width="200"/> | <img src="https://private-user-images.githubusercontent.com/200123932/551058904-1eb04162-345f-42b1-bc5a-41355ff691b7.jpeg?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NzEzNDQxOTIsIm5iZiI6MTc3MTM0Mzg5MiwicGF0aCI6Ii8yMDAxMjM5MzIvNTUxMDU4OTA0LTFlYjA0MTYyLTM0NWYtNDJiMS1iYzVhLTQxMzU1ZmY2OTFiNy5qcGVnP1gtQW16LUFsZ29yaXRobT1BV1M0LUhNQUMtU0hBMjU2JlgtQW16LUNyZWRlbnRpYWw9QUtJQVZDT0RZTFNBNTNQUUs0WkElMkYyMDI2MDIxNyUyRnVzLWVhc3QtMSUyRnMzJTJGYXdzNF9yZXF1ZXN0JlgtQW16LURhdGU9MjAyNjAyMTdUMTU1ODEyWiZYLUFtei1FeHBpcmVzPTMwMCZYLUFtei1TaWduYXR1cmU9Njk1NWVlNWJjMzFkNzNlNDVlNDY1YTE2NmMyZjcyZWZmMzdiZTdhYmYzODdiMjI5YjI3NzVhNjkxNDExN2I4YSZYLUFtei1TaWduZWRIZWFkZXJzPWhvc3QifQ.-NEWyAK6SxAwY2FVDiQsm8fXmi5c9uwnSCqBj4HHk_k" width="200"/> | <img src="https://private-user-images.githubusercontent.com/200123932/551058905-d9da6f0e-2095-4f0b-8c47-6a977217f5f2.jpeg?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NzEzNDQxOTIsIm5iZiI6MTc3MTM0Mzg5MiwicGF0aCI6Ii8yMDAxMjM5MzIvNTUxMDU4OTA1LWQ5ZGE2ZjBlLTIwOTUtNGYwYi04YzQ3LTZhOTc3MjE3ZjVmMi5qcGVnP1gtQW16LUFsZ29yaXRobT1BV1M0LUhNQUMtU0hBMjU2JlgtQW16LUNyZWRlbnRpYWw9QUtJQVZDT0RZTFNBNTNQUUs0WkElMkYyMDI2MDIxNyUyRnVzLWVhc3QtMSUyRnMzJTJGYXdzNF9yZXF1ZXN0JlgtQW16LURhdGU9MjAyNjAyMTdUMTU1ODEyWiZYLUFtei1FeHBpcmVzPTMwMCZYLUFtei1TaWduYXR1cmU9NTVjYzM5ZjU5NmE4NDZhZTRiM2QyZGVmYjFmNGY5YzNhZjEzZTRkNWUyZmM1MmM5ZDA0ZGI2YjFiMWNhODgwYyZYLUFtei1TaWduZWRIZWFkZXJzPWhvc3QifQ.MSGJqZOYX1O0Vr6qrG_H7w7Jj2oBzVlMMYnfduyH-ME" width="200"/> | <img src="https://private-user-images.githubusercontent.com/200123932/551058906-74065c0f-6c37-4d4c-9e5b-2f26d4a2750b.jpeg?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NzEzNDQxOTIsIm5iZiI6MTc3MTM0Mzg5MiwicGF0aCI6Ii8yMDAxMjM5MzIvNTUxMDU4OTA2LTc0MDY1YzBmLTZjMzctNGQ0Yy05ZTViLTJmMjZkNGEyNzUwYi5qcGVnP1gtQW16LUFsZ29yaXRobT1BV1M0LUhNQUMtU0hBMjU2JlgtQW16LUNyZWRlbnRpYWw9QUtJQVZDT0RZTFNBNTNQUUs0WkElMkYyMDI2MDIxNyUyRnVzLWVhc3QtMSUyRnMzJTJGYXdzNF9yZXF1ZXN0JlgtQW16LURhdGU9MjAyNjAyMTdUMTU1ODEyWiZYLUFtei1FeHBpcmVzPTMwMCZYLUFtei1TaWduYXR1cmU9NGE3NWIyMmFlMjZjMDYyNzJhZTBmYjMzMGU1OTUyMjZjNDE0NzMwNTI3ZWZlMjgzYTcyZTY2Y2NiNDkxNDllMCZYLUFtei1TaWduZWRIZWFkZXJzPWhvc3QifQ.mbcWBH0pfSnMnkds5S_G-E9xyIAdD_41-xVSVsZOztk" width="200"/> |

## 🧪 Testing Overview

The project includes:
* **CartDaoTest:** Validating database transactions and data integrity.
* **ProductRepositoryImplTest:** Mocking network responses to verify data flow and error handling.
* **CatalogViewModelTest:** Testing UI state logic, filtering, and sorting algorithms.

## 👨‍💻 Author
**Ramiz Galiakberov**
* Android Developer
* Astana, Kazakhstan
