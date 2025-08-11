package com.example.myapplication.domain.model.post_model


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostsUserResponse(
    @SerializedName("data")
    val `data`: List<Post>,
    @SerializedName("isGetData")
    val isGetData: Boolean,
    @SerializedName("statusCode")
    val statusCode: Int
)