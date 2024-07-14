package com.books.app.domain.usecase

import com.books.app.data.model.Book
import com.books.app.data.model.DataResult
import com.books.app.data.model.GenreType
import com.books.app.domain.repository.BooksRepository
import javax.inject.Inject

class AllBooksUseCase @Inject constructor(
    private val booksRepository: BooksRepository
) : ResultUseCase<Any, DataResult<Map<GenreType, List<Book>>>>() {

    override suspend fun doWork(params: Any?): DataResult<Map<GenreType, List<Book>>> {
        return try {
            val listOfBooks = booksRepository.getAllBooks()
            DataResult.Success(listOfBooks.groupBy { it.genre })
        } catch (e: Exception) {
            DataResult.Failure(e)
        }
    }
}