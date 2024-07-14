package com.books.app.domain.usecase

import com.books.app.data.model.BannerSlide
import com.books.app.data.model.DataResult
import com.books.app.domain.repository.BooksRepository
import javax.inject.Inject

class TopBannerSlidersUseCase @Inject constructor(
    private val booksRepository: BooksRepository
) : ResultUseCase<Any, DataResult<List<BannerSlide>>>() {

    override suspend fun doWork(params: Any?): DataResult<List<BannerSlide>> {
        return try {
            val listOfTopBannerSlides = booksRepository.getTopBannerSlides()
            DataResult.Success(listOfTopBannerSlides)
        } catch (e: Exception) {
            DataResult.Failure(e)
        }
    }
}