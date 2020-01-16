package com.systemtron.virtualtouristguide.features.kym.model

import kotlinx.serialization.Serializable

@Serializable
data class LatLong(
    var latitude: Double,
    var longitude: Double
)