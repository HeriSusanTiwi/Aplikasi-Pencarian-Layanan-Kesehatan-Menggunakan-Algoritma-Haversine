package com.herisusan.pencarianlayanankesehatan.data.model

import com.google.gson.annotations.SerializedName

class ModelDetail {
    @SerializedName("geometry")
    val modelGeometry: ModelGeometry? = null

    @SerializedName("name")
    val name: String? = null

    @SerializedName("formatted_address")
    val formatted_address: String? = null

    @SerializedName("formatted_phone_number")
    val formatted_phone_number: String? = null
}