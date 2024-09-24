package com.example.testovoebinomtech.controllers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testovoebinomtech.R
import com.example.testovoebinomtech.models.MarkerModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import org.osmdroid.util.GeoPoint

class MainScreenController(private val context : Context) : ViewModel() {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val _location = MutableLiveData<Location?>()
    val location: LiveData<Location?> = _location

    private val _markers = MutableLiveData<List<MarkerModel>>()
    val markers: LiveData<List<MarkerModel>> get() = _markers

    val isDialogShow : MutableState<Boolean> = mutableStateOf(false)

    private var locationRequest: LocationRequest


    init {
        val initialMarkers = listOf(
            MarkerModel(
                position = GeoPoint(55.9558, 37.8173),
                title = "Вин",
                date = "15.09.24",
                time = "12:00",
                imageId = R.drawable.vin
            ),
            MarkerModel(
                position = GeoPoint(55.751244, 37.618423),
                title = "Сильвестр",
                date = "17.09.24",
                time = "16:30",
                imageId = R.drawable.silvestr
            ),
            MarkerModel(
                position = GeoPoint(55.851999, 37.417734),
                title = "Джейсон",
                date = "23.09.24",
                time = "20:00",
                imageId = R.drawable.statham

            )
        )
        _markers.value = initialMarkers

        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setDurationMillis(5000)
            .build()
    }

    fun startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation?.let { location ->
                _location.value = location
            }
        }
    }

    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    fun dialogActive()
    {
        isDialogShow.value = true
    }

    fun dialogDisable()
    {
        isDialogShow.value = false
    }

}