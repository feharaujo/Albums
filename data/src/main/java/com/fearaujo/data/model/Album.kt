package com.fearaujo.data.model

import com.google.gson.annotations.SerializedName

data class Album(
        @SerializedName("userId") var userId: Int,
        @SerializedName("id") var id: Int,
        @SerializedName("title") var title: String
)