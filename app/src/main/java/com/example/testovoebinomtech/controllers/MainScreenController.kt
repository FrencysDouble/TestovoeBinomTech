package com.example.testovoebinomtech.controllers

import android.Manifest
import android.app.Application
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
import com.google.android.gms.location.LocationServices
import org.osmdroid.util.GeoPoint

class MainScreenController(private val context : Context) : ViewModel() {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val _location = MutableLiveData<Location?>()
    val location: LiveData<Location?> = _location

    private val _markers = MutableLiveData<List<MarkerModel>>()
    val markers: LiveData<List<MarkerModel>> get() = _markers

    val isDialogShow : MutableState<Boolean> = mutableStateOf(false)

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
    }

    fun updateLocation() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    _location.value = location
                }
                .addOnFailureListener {
                }
        } else {
            _location.value = null
        }
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