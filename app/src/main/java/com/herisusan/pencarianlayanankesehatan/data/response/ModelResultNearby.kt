package com.herisusan.pencarianlayanankesehatan.data.response

import com.google.gson.annotations.SerializedName
import com.herisusan.pencarianlayanankesehatan.data.model.ModelResult

class ModelResultNearby {

    @SerializedName("results")
    val modelResult: List<ModelResult> = emptyList()
}