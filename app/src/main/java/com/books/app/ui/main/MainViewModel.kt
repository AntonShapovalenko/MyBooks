package com.books.app.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.books.app.data.model.BannerSlide
import com.books.app.data.model.Book
import com.books.app.data.model.DataResult
import com.books.app.data.model.GenreType
import com.books.app.domain.usecase.AllBooksUseCase
import com.books.app.domain.usecase.TopBannerSlidersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    allBooksUseCase: AllBooksUseCase,
    topBannerSlidersUseCase: TopBannerSlidersUseCase
) : ViewModel() {

    private val allBooks: StateFlow<DataResult<Map<GenreType, List<Book>>>> =
        allBooksUseCase.invoke().stateIn(viewModelScope, SharingStarted.Eagerly, DataResult.Loading)

    private val topBannerSlides: StateFlow<DataResult<List<BannerSlide>>> =
        topBannerSlidersUseCase.invoke().stateIn(
            viewModelScope, SharingStarted.Eagerly, DataResult.Loading
        )

    val mainScreenState = combine(allBooks, topBannerSlides) { allBooks, topBannerSlides ->
        val isLoading = allBooks is DataResult.Loading || topBannerSlides is DataResult.Loading
        val isFailure = allBooks is DataResult.Failure || topBannerSlides is DataResult.Failure

        val topBannerSlidesList = if (topBannerSlides is DataResult.Success) {
            topBannerSlides.data
        } else {
            emptyList()
        }
        val allBooksMap = if (allBooks is DataResult.Success) {
            allBooks.data.map {
                BookGenreData(genreType = it.key, books = it.value)
            }
        } else {
            emptyList()
        }
        MainState(
            isLoading = isLoading,
            isFailure = isFailure,
            topBannerSlides = topBannerSlidesList,
            books = allBooksMap
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, MainState(isLoading = true))
}


data class BookGenreData(val genreType: GenreType, val books: List<Book>)

data class MainState(
    val isLoading: Boolean = false,
    val isFailure: Boolean = false,
    val topBannerSlides: List<BannerSlide> = emptyList(),
    val books: List<BookGenreData> = emptyList()
)