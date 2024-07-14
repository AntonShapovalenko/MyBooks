package com.books.app.data

import com.books.app.data.model.BannerSlide
import com.books.app.data.model.Book
import com.books.app.data.model.DataBooks
import com.books.app.data.model.DetailsCarousel
import com.books.app.domain.repository.BooksRepository
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson

class BooksRepositoryImpl(private val instanceFbc: FirebaseRemoteConfig) : BooksRepository {

     private suspend fun getAllJsonData(): String {
        return instanceFbc.getString(KEY_JSON_DATA)
    }

    override suspend fun getAllBooks(): List<Book> {
        val json = getAllJsonData()
        val dataBooks = Gson().fromJson(json, DataBooks::class.java)
        return dataBooks.books
    }

    override suspend fun getTopBannerSlides(): List<BannerSlide> {
        val json = getAllJsonData()
        val dataBooks = Gson().fromJson(json, DataBooks::class.java)
        return dataBooks.topBannerSlides
    }

    override suspend fun getYouWillLikeIds(): List<Int> {
        val json = getAllJsonData()
        val dataBooks = Gson().fromJson(json, DataBooks::class.java)
        return dataBooks.youWillLike
    }

    override suspend fun getDetailsCarousel(): List<Book> {
        val dataString = instanceFbc.getString(KEY_DETAILS_CAROUSEL)
        val gson = Gson().fromJson(dataString, DetailsCarousel::class.java)
        return gson.books
    }

    companion object {
        private const val KEY_DETAILS_CAROUSEL = "details_carousel"
        private const val KEY_JSON_DATA = "json_data"
    }
}