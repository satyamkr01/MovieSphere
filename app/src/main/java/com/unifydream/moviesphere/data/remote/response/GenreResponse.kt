package com.unifydream.moviesphere.data.remote.response

import com.unifydream.moviesphere.data.models.Genre
import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres")
    val genres: List<Genre>
)