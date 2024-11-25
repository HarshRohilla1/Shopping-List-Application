package com.example.myshoppinglistapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myshoppinglistapp.Screens.AddEditScreen
import com.example.myshoppinglistapp.Screens.HomeScreen
import com.example.myshoppinglistapp.Screens.Screen
import com.example.myshoppinglistapp.Screens.StoreNavigationScreen

@Composable
fun Navigation(viewModel: LocationViewModel=viewModel(),
               navController:NavHostController= rememberNavController(),
               modifier: Modifier)
{
    val context = LocalContext.current
    val locationutils = LocationUtils(context)

    NavHost(navController = navController,
        startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route)
        {
            HomeScreen(navController,viewModel,locationutils,context)
        }

        composable(Screen.AddScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                }
            )){entry->
            val id=if(entry.arguments != null)entry.arguments!!.getLong("id") else 0L
            AddEditScreen(id,navController,viewModel,locationutils,context)

        }
        composable(Screen.StoreNavigationScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                }
            )
        ){entry->
            val id = if(entry.arguments != null)entry.arguments!!.getLong("id") else 0L
            StoreNavigationScreen(
                id,
                navController,
                viewModel
            )
        }
        dialog("locationScreen") {backtrack->
            viewModel.location.value?.let{it1->

                locationselectionScreen(location = it1, onLocationSelected =
                {locationData->
                    viewModel.fetchAddress("${locationData.latitude},${locationData.longitude}")
                    navController.popBackStack()
                })
            }

        }
    }

}