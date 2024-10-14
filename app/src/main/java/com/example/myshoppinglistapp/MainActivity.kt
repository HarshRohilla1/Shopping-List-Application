package com.example.myshoppinglistapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.example.myshoppinglistapp.ui.theme.MyShoppingListAppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyShoppingListAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {

                  Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation()
{
    val navController = rememberNavController()
    val viewModel:LocationViewModel = viewModel()
    val context = LocalContext.current
    val locationutils = LocationUtils(context)

    NavHost(navController = navController, startDestination = "shoppingListScreen") {
        composable("shoppingListScreen") {
            ShoppingListAndroid(
                LocationUtils = locationutils,
                address = viewModel.address.value.firstOrNull()?.formatted_address ?: "No Address",
                viewModel = viewModel,
                navController = navController,
                context = context
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


