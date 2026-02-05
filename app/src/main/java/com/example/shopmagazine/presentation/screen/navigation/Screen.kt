package com.example.shopmagazine.presentation.screen.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object Catalog : Screen("catalog_screen")
    object Cart : Screen("cart_screen")
}