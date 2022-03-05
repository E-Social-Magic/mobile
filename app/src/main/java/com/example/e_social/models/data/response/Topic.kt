package com.example.e_social.models.data.response


import com.google.gson.annotations.SerializedName

data class Topic(
    val height: Int,
    @SerializedName("held_items")
    val id: Int,
    @SerializedName("is_default")
    val isDefault: Boolean,
    @SerializedName("location_area_encounters")
    val locationAreaEncounters: String,
    val name: String,
    val order: Int,
    @SerializedName("past_types")
    val weight: Int,
    val url: String
)