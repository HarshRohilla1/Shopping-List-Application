package com.example.myshoppinglistapp


import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.http.Header

class LocationViewModel:ViewModel() {

    private val _location = mutableStateOf<LocationData?>(null)
    val location: State<LocationData?> =  _location

    private val _address = mutableStateOf(listOf<Geocodingresult>())
    val address: State<List<Geocodingresult>> =  _address

    fun updateLocation(newlocation: LocationData) {
        _location.value = newlocation
    }

    fun fetchAddress(latLng: String)
    {
        try{
            viewModelScope.launch {

                    val result = RetrofitClient.create().getAddressFromCoordinates(
                        "com.example.myshoppinglistapp", "DDF1F9AA7F49BC7644A3F08F6D7CBF86B414B583", latLng,"AIzaSyDCvlS76LQ31VyvHEndbVrrOEHDXju_7U4")
                    _address.value=result.results

            }
        }
        catch (e:Exception){

            Log.d("res1","${e.cause},${e.message}")

        }
    }
 }