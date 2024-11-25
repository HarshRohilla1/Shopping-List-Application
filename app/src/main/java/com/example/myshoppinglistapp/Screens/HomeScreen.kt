package com.example.myshoppinglistapp.Screens

import android.Manifest
import android.content.Context
import android.icu.text.CaseMap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.example.myshoppinglistapp.LocationUtils
import com.example.myshoppinglistapp.LocationViewModel
import com.example.myshoppinglistapp.MainActivity
import com.example.myshoppinglistapp.R
import com.example.myshoppinglistapp.data.Item

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController,
               viewModel: LocationViewModel,
               LocationUtils: LocationUtils,
               context: Context)
{

    Scaffold(
        topBar = {
            Appbar( title = "ShoppingList")
        },

        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 25.dp),
                containerColor = colorResource(id = R.color.purple_500),
                contentColor = Color.White,
                onClick = {
                    navController.navigate(Screen.AddScreen.route + "/0L")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )

            }

        },
        content = {
            val itemList= viewModel.getAllItems.collectAsState(initial = listOf())
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                .fillMaxSize()
                .background(color = colorResource(id = R.color.card)))
            {
              items(itemList.value, key = {item->item.id})
              {item->
                  val dismissState = rememberDismissState(
                      confirmValueChange = {
                          if(it== DismissValue.DismissedToEnd||it== DismissValue.DismissedToStart){
                              viewModel.deleteItem(item)
                          }
                          true
                      }
                  )

                  SwipeToDismiss(
                      state = dismissState,
                      background = {
                          val color by animateColorAsState(
                              if (dismissState.dismissDirection
                                  == DismissDirection.EndToStart|| dismissState.dismissDirection == DismissDirection.StartToEnd) Color.Red else Color.Transparent
                              ,label = ""
                          )
                          val alignment = Alignment.CenterEnd
                          Box(
                              modifier = Modifier
                                  .fillMaxSize()
                                  .background(color)
                                  .padding(horizontal = 20.dp),
                              contentAlignment = alignment
                          )
                          {
                              Icon(Icons.Default.Delete, contentDescription = "Delete Icon", tint = Color.White )
                          }

                      },
                      directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                      dismissContent = {
                          Item(item = item){
                              val id=item.id
                              navController.navigate(Screen.AddScreen.route+"/$id")
                          }
                      }
                  )

              }

            }

        }
    )



}

@Composable
fun Item(item: Item,onClick:()-> Unit)
{

    Card( modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clickable { onClick() },
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        colors = CardDefaults.cardColors(Color.White)
    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ){
            Text(text =item.name, fontWeight = FontWeight.ExtraBold, color = Color.Black)
            Text(text = item.quantity, color = Color.Black)
            Text(text = item.address, color= Color.Black)
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Appbar(title:String,
           onNavigationIconClick:()->Unit = {} ){
    val navigationIcon: (@Composable () -> Unit)? =
    {
        if(title!="ShoppingList")
        {
            IconButton(onClick = { onNavigationIconClick() }) {

                Icon(modifier = Modifier.padding(16.dp),
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }
        else
        {
            null
        }
    }
    TopAppBar(

            title = {
                Text(
                    text = title,
                    color = Color.White,
                    modifier = Modifier
                        .heightIn(max = 30.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
            },
            navigationIcon = {navigationIcon},
            colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(R.color.card))
        )
    }


@Preview
@Composable
fun homescreenpreview()
{
    HomeScreen(navController = NavController(MainActivity()), viewModel = LocationViewModel(), LocationUtils = LocationUtils(
        LocalContext.current
    ), context = MainActivity())
}

