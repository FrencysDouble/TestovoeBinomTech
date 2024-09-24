package com.example.testovoebinomtech.models

import org.osmdroid.util.GeoPoint

data class MarkerModel(
    val position : GeoPoint,
    val title : String,
    val date : String,
    val time : String,
    val imageId : Int
)
