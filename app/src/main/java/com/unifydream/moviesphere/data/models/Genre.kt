package com.unifydream.moviesphere.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String
): Parcelable
