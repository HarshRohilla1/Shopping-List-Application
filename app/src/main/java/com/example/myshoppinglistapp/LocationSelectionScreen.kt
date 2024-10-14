package com.example.myshoppinglistapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun locationselectionScreen(
    location:LocationData,
    onLocationSelected:(LocationData)->Unit
)
{
    val userLocation = remember{
        mutableStateOf(LatLng(location.latitude,location.longitude))
    }


    val CameraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(userLocation.value,10f)
    }
    
    Column(modifier = Modifier.fillMaxSize()){


        GoogleMap(modifier = Modifier
            .padding(16.dp)
            .weight(1f),
            cameraPositionState = CameraPositionState,
            onMapClick = {
                userLocation.value = it
            }
        ){
            Marker(state= MarkerState(position = userLocation.value)){

            }

        }

        var newloaction:LocationData

        Button(onClick = {
            newloaction = LocationData(userLocation.value.latitude,userLocation.value.longitude)
            onLocationSelected(newloaction)
        }) {

            Text(text = "Set Location")
            
        }
        
    }
}