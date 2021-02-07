package com.pramodk.retrofitdemo.api


import com.google.gson.annotations.SerializedName

data class CatJson(
    val createdAt: String,
    val deleted: Boolean,
    @SerializedName("_id")
    val id: String,
    val source: String,
    val status: Status,
    val text: String,
    val type: String,
    val updatedAt: String,
    val used: Boolean,
    val user: String,
    @SerializedName("__v")
    val v: Int
)