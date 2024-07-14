package com.books.app.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.books.app.data.model.Book
import com.books.app.data.model.DataResult
import com.books.app.domain.usecase.BookCarouselUseCase
import com.books.app.domain.usecase.YouWillLikeUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = DetailsViewModel.DetailViewModelFactory::class)
class DetailsViewModel @AssistedInject constructor(
    bookCarouselUseCase: BookCarouselUseCase,
    youWillLikeUseCase: YouWillLikeUseCase,
    @Assisted val selectedBooksId: Int,
) : ViewModel() {

    @AssistedFactory
    interface DetailViewModelFactory {
        fun create(selectedBooksId: Int): DetailsViewModel
    }

    private val booksByGenre: StateFlow<DataResult<List<Book>>> =
        bookCarouselUseCase.invoke()
            .stateIn(viewModelScope, SharingStarted.Eagerly, DataResult.Loading)

    // this is books that you can like
    private val booksYouWillLike: StateFlow<DataResult<List<Book>>> =
        youWillLikeUseCase.invoke()
            .stateIn(viewModelScope, SharingStarted.Eagerly, DataResult.Loading)

    val detailsScreenState =
        combine(booksByGenre, booksYouWillLike) { booksByGenre, booksYouWillLike ->

            val isLoading =
                booksByGenre is DataResult.Loading || booksYouWillLike is DataResult.Loading
            val isFailure =
                booksByGenre is DataResult.Failure || booksYouWillLike is DataResult.Failure

            val booksByGenreList = if (booksByGenre is DataResult.Success) {
                booksByGenre.data
            } else {
                emptyList()
            }
            val booksYouWillLikeList = if (booksYouWillLike is DataResult.Success) {
                booksYouWillLike.data
            } else {
                emptyList()
            }

            val selectedBooksIndex =
                booksByGenreList.indexOf(booksByGenreList.find { it.id == selectedBooksId })

            DetailsState(
                isLoading = isLoading,
                isFailure = isFailure,
                books = booksByGenreList,
                booksYouWillLike = booksYouWillLikeList,
                selectedBooksIndex = selectedBooksIndex
            )
        }.stateIn(viewModelScope, SharingStarted.Eagerly, DetailsState(isLoading = true))

}


data class DetailsState(
    val isLoading: Boolean = false,
    val isFailure: Boolean = false,
    val books: List<Book> = emptyList(),
    val booksYouWillLike: List<Book> = emptyList(),
    val selectedBooksIndex: Int = 0
)