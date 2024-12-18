package com.unifydream.moviesphere.data.remote.response

import com.unifydream.moviesphere.data.models.Cast
import com.google.gson.annotations.SerializedName

data class CastResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("cast")
    val castResult: List<Cast>
)
