package com.books.app.data.model

import com.google.gson.annotations.SerializedName

data class DataBooks(
    val books: List<Book>,
    @SerializedName("top_banner_slides") val topBannerSlides: List<BannerSlide>,
    @SerializedName("you_will_like_section") val youWillLike: List<Int>,
)
