package com.books.app.domain.usecase

import com.books.app.data.model.Book
import com.books.app.data.model.DataResult
import com.books.app.domain.repository.BooksRepository
import javax.inject.Inject

class BookCarouselUseCase @Inject constructor(
    private val booksRepository: BooksRepository
) : ResultUseCase<Any, DataResult<List<Book>>>() {

    override suspend fun doWork(params: Any?): DataResult<List<Book>> {
        return try {
            val listBooks = booksRepository.getDetailsCarousel()
            DataResult.Success(listBooks)
        } catch (e: Exception) {
            DataResult.Failure(e)
        }
    }
}