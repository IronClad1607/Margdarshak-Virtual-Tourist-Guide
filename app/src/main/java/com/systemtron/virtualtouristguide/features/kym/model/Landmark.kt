package com.systemtron.virtualtouristguide.features.kym.model

import com.google.firebase.ml.vision.common.FirebaseVisionLatLng
import kotlinx.serialization.Serializable


@Serializable
data class Landmark(
    var landmark: String,
    var confidence: Float,
    var location: ArrayList<LatLong>
)