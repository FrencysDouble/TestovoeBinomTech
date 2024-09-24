package com.example.testovoebinomtech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.testovoebinomtech.main_ui.MainScreen
import com.example.testovoebinomtech.di.MyApp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.osmdroid.config.Configuration

class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        Configuration.getInstance().load(this, getSharedPreferences("osmdroid", MODE_PRIVATE))

        val coreSupervisor = (application as MyApp).cs

        val controllers = with(coreSupervisor)
        {
            getApplicationControllers()
        }
        setContent {
            MainScreen(controllers.provideMainScreenController())
        }
    }
}
