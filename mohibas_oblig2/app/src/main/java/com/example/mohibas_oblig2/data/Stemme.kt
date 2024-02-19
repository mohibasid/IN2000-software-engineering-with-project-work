package com.example.mohibas_oblig2.data

import com.google.gson.annotations.SerializedName

data class StemmeJson(
    @SerializedName("id")
    var id: String,
)

data class StemmeXml(
    val stemmer:  Int?,
    val id: Int?
)
