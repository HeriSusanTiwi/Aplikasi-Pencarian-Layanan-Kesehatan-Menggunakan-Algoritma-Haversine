package com.herisusan.pencarianlayanankesehatan.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ModelResult : Serializable {
    @SerializedName("geometry")
    val modelGeometry: ModelGeometry? = null

    @SerializedName("name")
    val name: String? = null

    @SerializedName("vicinity")
    val vicinity: String? = null

    @SerializedName("place_id")
    val placeId: String? = null
}