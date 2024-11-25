package com.example.myshoppinglistapp.Screens

import android.Manifest
import android.content.Context
import android.text.Layout
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.example.myshoppinglistapp.LocationUtils
import com.example.myshoppinglistapp.LocationViewModel
import com.example.myshoppinglistapp.MainActivity
import com.example.myshoppinglistapp.R
import com.example.myshoppinglistapp.data.Item
import kotlinx.coroutines.launch

@Composable
fun AddEditScreen(
    id: Long,
    navController: NavController,
    viewModel: LocationViewModel,
    LocationUtils: LocationUtils,
    context: Context
) {
    val scope = rememberCoroutineScope()
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                && permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            ) {
                LocationUtils.requestLocationUpdates(viewModel = viewModel)

            } else {
                val rationalRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                if (rationalRequired) {
                    Toast.makeText(context, "Location Permission Required", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, "Go to Settings to Give Permission", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        })

    if (id != 0L) {
        val item = viewModel.getAItemById(id).collectAsState(initial = Item(0L, "", "", ""))
        viewModel.Itemstate = item.value.name
        viewModel.quantitystate = item.value.quantity
        viewModel.addressstate = item.value.address
    } else {
        viewModel.Itemstate = ""
        viewModel.quantitystate = ""
        viewModel.addressstate = ""
    }

    Scaffold(
        topBar = {
            Appbar(
                title = if (id != 0L) stringResource(id = R.string.update_item)
                else stringResource(id = R.string.add_item),
                { navController.navigateUp() }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Spacer(modifier = Modifier.height(10.dp))

            ItemTextfield(label = "Item",
                value = viewModel.Itemstate,
                onValueChange = { viewModel.onItemNameChanged(it) })

            Spacer(modifier = Modifier.height(10.dp))

            ItemTextfield(label = "Quantity",
                value = viewModel.quantitystate,
                onValueChange = { viewModel.onItemQuantityChanged(it) })
            Spacer(modifier = Modifier.height(20.dp))

            Row()
            {
                Button(
                    onClick = {
                        if(viewModel.addressstate.isNotEmpty())
                        {
                            if(id != 0L){
                                viewModel.updateItem(
                                    Item(
                                        id=id,
                                        address = viewModel.address.value.firstOrNull()?.formatted_address ?: "No Address"
                                    )
                                )
                            }
                            else
                            {
                                viewModel.addItem(
                                    Item(
                                        address = viewModel.address.value.firstOrNull()?.formatted_address ?: "No Address"
                                    )
                                )

                            }
                        }
                        scope.launch{
                            if(LocationUtils.hasLocationPermission(context)){
                                LocationUtils.requestLocationUpdates(viewModel)
                                navController.navigate("locationScreen"){
                                    this.launchSingleTop
                                }
                            }
                            else{
                                requestPermissionLauncher.launch(arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                ))

                            }
                        }

                    },colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.teal_700)
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {  Text(
                    if (id != 0L) stringResource(id = R.string.change_address)
                    else stringResource(id = R.string.add_address),
                    style = TextStyle(
                        fontSize = 18.sp
                    ),
                    color = Color.White
                )
                }

                Spacer(modifier = Modifier.width(10.dp) )
                Button(
                    onClick = {
                        if (viewModel.Itemstate.isNotEmpty() &&
                            viewModel.quantitystate.isNotEmpty()
                        ) {
                            if (id != 0L) {
                                viewModel.updateItem(
                                    Item(
                                        id = id,
                                        name = viewModel.Itemstate.trim(),
                                        quantity = viewModel.quantitystate.trim(),
                                        address = viewModel.address.value.firstOrNull()?.formatted_address ?: "No Address"
                                    )
                                )
                            } else {
                                viewModel.addItem(
                                    Item(
                                        name = viewModel.Itemstate.trim(),
                                        quantity = viewModel.quantitystate.trim(),
                                        address = viewModel.address.value.firstOrNull()?.formatted_address ?: "No Address"
                                        )
                                )
                            }

                        }
                        scope.launch {
                            navController.navigateUp()

                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.card)
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
                {
                    Text(
                        if (id != 0L) stringResource(id = R.string.update_item)
                        else stringResource(id = R.string.add_item),
                        style = TextStyle(
                            fontSize = 18.sp
                        ),
                        color = Color.White
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemTextfield(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.card),
            unfocusedBorderColor = colorResource(id = R.color.card),
            focusedLabelColor = colorResource(id = R.color.card),
            unfocusedLabelColor = colorResource(id = R.color.card),
            cursorColor = colorResource(id = R.color.card)
        )
    )

}