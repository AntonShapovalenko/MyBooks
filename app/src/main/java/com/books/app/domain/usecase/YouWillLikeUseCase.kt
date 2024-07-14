package com.books.app.domain.usecase

import com.books.app.data.model.Book
import com.books.app.data.model.DataResult
import com.books.app.domain.repository.BooksRepository
import javax.inject.Inject

class YouWillLikeUseCase @Inject constructor(
    private val booksRepository: BooksRepository
) : ResultUseCase<Any, DataResult<List<Book>>>() {

    override suspend fun doWork(params: Any?): DataResult<List<Book>> {
        return try {
            val allBooksList = booksRepository.getAllBooks()
            val youWillLikeIds = booksRepository.getYouWillLikeIds()
            val listOfLikedBooks = mutableListOf<Book>()
            youWillLikeIds.forEach { id->
                val book = allBooksList.find { it.id == id }
                if (book != null) {
                    listOfLikedBooks.add(book)
                }
            }
            DataResult.Success(listOfLikedBooks)
        } catch (e: Exception) {
            DataResult.Failure(e)
        }
    }
}