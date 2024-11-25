package com.example.myshoppinglistapp.Screens

sealed class Screen(val route:String) {

    object HomeScreen : Screen("home_screen")
    object AddScreen: Screen("add_screen")
    object StoreNavigationScreen : Screen("storeNavigation_screen")

}