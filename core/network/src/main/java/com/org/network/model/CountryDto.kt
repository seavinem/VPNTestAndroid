package com.org.network.model

import com.google.gson.annotations.SerializedName

data class CountryDto(
    @SerializedName("name")
    val name: CountryNameDto?,
    @SerializedName("capital")
    val capital: List<String>?,
    @SerializedName("flags")
    val flags: CountryFlagsDto?
)

data class CountryNameDto(
    @SerializedName("common")
    val common: String?
)

data class CountryFlagsDto(
    @SerializedName("svg")
    val svg: String?
)