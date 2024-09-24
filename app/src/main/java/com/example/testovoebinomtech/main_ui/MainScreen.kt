package com.example.testovoebinomtech.main_ui

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Popup
import androidx.core.content.ContextCompat
import com.example.testovoebinomtech.R
import com.example.testovoebinomtech.controllers.MainScreenController
import com.example.testovoebinomtech.main_ui.CustomIcons.createCustomMarker
import com.example.testovoebinomtech.models.MarkerModel
import com.example.testovoebinomtech.ui.theme.BlueIcons
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MainScreen(mainScreenController: MainScreenController) {
    var mapView by remember { mutableStateOf<MapView?>(null) }

    var hasLocationPermission by remember { mutableStateOf(false) }

    val location by mainScreenController.location.observeAsState()

    var userMarker by remember { mutableStateOf<Marker?>(null) }

    val markers by mainScreenController.markers.observeAsState(emptyList())

    var currentMarkerIndex by remember { mutableIntStateOf(0) }

    val isDialogShow by remember { derivedStateOf { mainScreenController.isDialogShow } }


    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            hasLocationPermission = isGranted
            if (isGranted) {
                mainScreenController.updateLocation()
            }
        }
    )

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                hasLocationPermission = true
                mainScreenController.updateLocation()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    var showInfoWindow by remember { mutableStateOf(false) }
    var selectedMarkerData by remember { mutableStateOf<MarkerModel?>(null) }

    if (isDialogShow.value) {
        selectedMarkerData?.let {
            BottomSheetDialog(it) {
                mainScreenController.dialogDisable()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                MapView(context).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    controller.setZoom(9.5)
                    zoomController.setVisibility(org.osmdroid.views.CustomZoomButtonsController.Visibility.NEVER)
                    setMultiTouchControls(true)

                    mapView = this

                    userMarker = Marker(this).apply {
                        icon = ContextCompat.getDrawable(context, R.drawable.ic_my_tracker_46dp)
                        setAnchor(Marker.ANCHOR_BOTTOM, Marker.ANCHOR_BOTTOM)
                        mapView?.overlays?.add(this)
                    }
                    markers.forEach { markerData ->
                        Marker(this).apply {
                            position = markerData.position
                            title = markerData.title
                            val customMarkerBitmap = createCustomMarker(context, markerData.imageId)
                            icon = BitmapDrawable(resources, customMarkerBitmap)
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                            setOnMarkerClickListener { _, _ ->
                                selectedMarkerData = markerData
                                showInfoWindow = true
                                true
                            }

                            mapView?.overlays?.add(this)
                        }
                    }
                }
            },
            update = { mapView ->
                location?.let { loc ->
                    val newPoint = GeoPoint(loc.latitude, loc.longitude)

                    userMarker?.position = newPoint
                    userMarker?.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                    mapView.controller.setCenter(newPoint)

                    mapView.invalidate()
                }
            }
        )

        if (showInfoWindow && selectedMarkerData != null) {
            mainScreenController.dialogActive()
            Popup(
                alignment = Alignment.Center,
                onDismissRequest = { showInfoWindow = false }
            ) {
                selectedMarkerData?.let{CustomInfoWindow(it)}
            }
            }

        Column(
            Modifier.fillMaxWidth().padding(end = 12.dp, top = 20.dp),
            horizontalAlignment = Alignment.End
        ) {
            IconButton(
                onClick = { mapView?.controller?.zoomIn() },
                Modifier.size(50.dp)
            ) {
                Box(Modifier.wrapContentSize().background(Color.White)) {
                    Icon(
                        painterResource(R.drawable.ic_zoom_plus_55dp),
                        contentDescription = null,
                        tint = BlueIcons
                    )
                }
            }

            Spacer(Modifier.padding(10.dp))

            IconButton(
                onClick = { mapView?.controller?.zoomOut() },
                Modifier.size(50.dp)
            ) {
                Box(Modifier.wrapContentSize().background(Color.White)) {
                    Icon(
                        painterResource(R.drawable.ic_zoom_minus_55dp),
                        contentDescription = null,
                        tint = BlueIcons
                    )
                }
            }

            Spacer(Modifier.padding(10.dp))

            IconButton(
                onClick = {
                    location?.let { loc ->
                        mapView?.controller?.setCenter(GeoPoint(loc.latitude, loc.longitude))
                    }
                },
                Modifier.size(50.dp)
            ) {
                Box(Modifier.wrapContentSize().background(Color.White)) {
                    Icon(
                        painterResource(R.drawable.ic_mylocation_55dp),
                        contentDescription = null,
                        tint = BlueIcons
                    )
                }
            }

            Spacer(Modifier.padding(10.dp))

            IconButton(onClick = {
                if (markers.isNotEmpty()) {
                    currentMarkerIndex = (currentMarkerIndex + 1) % markers.size

                    val nextMarkerPosition = markers[currentMarkerIndex].position
                    mapView?.controller?.setCenter(nextMarkerPosition)
                }
            }, Modifier.size(50.dp)) {
                Box(Modifier.wrapContentSize().background(Color.White)) {
                    Icon(
                        painterResource(R.drawable.ic_next_tracker_55dp),
                        contentDescription = null,
                        tint = BlueIcons
                    )
                }
            }
        }
    }
}

@Composable
fun CustomInfoWindow(markerData: MarkerModel)
{
    Box(Modifier.size(width = 150.dp, height = 60.dp)
        .background(Color.White, RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.Center)
    {
        Column {
            Text(markerData.title)
            Row()
            {
                Text("GPS, ")
                Text(markerData.time)
            }
        }
    }

}



