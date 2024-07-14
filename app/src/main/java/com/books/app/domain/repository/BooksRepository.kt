package com.books.app.domain.repository

import com.books.app.data.model.BannerSlide
import com.books.app.data.model.Book

interface BooksRepository {
    suspend fun getAllBooks(): List<Book>
    suspend fun getTopBannerSlides(): List<BannerSlide>
    suspend fun getYouWillLikeIds(): List<Int>
    suspend fun getDetailsCarousel(): List<Book>
}