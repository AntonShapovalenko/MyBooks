package com.books.app.data.model

import com.google.gson.annotations.SerializedName

data class Book(
    val id: Int,
    val name: String,
    val author: String,
    val summary: String,
    val genre: GenreType,
    @SerializedName("cover_url") val coverUrl: String,
    val views: String,
    val likes: String,
    val quotes: String
)

 enum class GenreType{
     Fantasy, Science, Romance
 }