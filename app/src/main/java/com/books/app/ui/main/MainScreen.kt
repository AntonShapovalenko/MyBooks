package com.books.app.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.books.app.R
import com.books.app.data.model.BannerSlide
import com.books.app.data.model.Book
import com.books.app.ui.common.title
import com.books.app.ui.theme.DarkGray
import com.books.app.ui.theme.White70
import kotlinx.coroutines.delay

@Composable
fun MainScreen(onOpenDetails: (Int) -> Unit) {

    val viewModel: MainViewModel = hiltViewModel()

    val mainScreenUiState by viewModel.mainScreenState.collectAsState()
    MainScreenContent(mainScreenUiState = mainScreenUiState, onOpenDetails = onOpenDetails)
}

@Composable
private fun MainScreenContent(
    mainScreenUiState: MainState,
    onOpenDetails: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DarkGray)
            .navigationBarsPadding()
    ) {

        Text(
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp, bottom = 8.dp)
                .statusBarsPadding(),
            text = stringResource(id = R.string.main_screen_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        if (mainScreenUiState.isFailure) {
            // Show Failed Screen here
        } else if (mainScreenUiState.isLoading) {
            // Show Loading Screen here
        } else {
            val booksData = mainScreenUiState.books
            val topBannerSlides = mainScreenUiState.topBannerSlides

            LazyColumn(
                modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 8.dp)
            ) {
                item("topBannerSlides") {
                    TopBannerSlidesContent(
                        topBannerSlides = topBannerSlides,
                        onOpenDetails = onOpenDetails
                    )
                }
                items(items = booksData, key = { it.genreType.name }) { bookData ->
                    BooksGenreRow(bookData, onOpenDetails)
                }
            }
        }
    }
}

@Composable
private fun BooksGenreRow(bookData: BookGenreData, onOpenDetails: (Int) -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.W700,
            text = bookData.genreType.title(),
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(bookData.books, key = { it.id }) { book ->
                BookItem(
                    book = book,
                    onOpenDetails = onOpenDetails
                )
            }
        }
    }
}

@Composable
private fun BookItem(book: Book, onOpenDetails: (Int) -> Unit) {
    Column(
        Modifier
            .width(120.dp)
            .clickable { onOpenDetails(book.id) }
    ) {
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .height(150.dp)
                .fillMaxWidth()
                .background(Color.Gray, shape = RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            painter = rememberAsyncImagePainter(book.coverUrl),
            contentDescription = "Image book cover",
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = book.name,
            maxLines = 2,
            fontSize = 16.sp,
            color = White70,
            fontWeight = FontWeight.W600,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun TopBannerSlidesContent(
    topBannerSlides: List<BannerSlide>,
    onOpenDetails: (Int) -> Unit
) {
    Box {
        val pageCount = topBannerSlides.size

        val pagerState =
            rememberPagerState(initialPage = Int.MAX_VALUE / 2, pageCount = { Int.MAX_VALUE })

        AutoPlayEffect(pagerState = pagerState)

        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp),
            state = pagerState,
            beyondViewportPageCount = 1,

            ) { i ->

            val index = i % pageCount

            topBannerSlides.getOrNull(index)?.let { slide ->
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            onOpenDetails(slide.bookId)
                        },
                    painter = rememberAsyncImagePainter(slide.cover),
                    contentDescription = "Image banner",
                    contentScale = ContentScale.Crop
                )
            }
        }
        Row(
            Modifier
                .height(20.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage % pageCount == iteration) MaterialTheme.colorScheme.primary
                    else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .background(color, CircleShape)
                        .size(6.dp)
                )
            }
        }
    }
}

@Composable
private fun AutoPlayEffect(pagerState: PagerState) {
    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(3000)
            with(pagerState) {
                val targetPage =
                    if (currentPage + 1 < Int.MAX_VALUE) currentPage + 1 else Int.MAX_VALUE / 2
                animateScrollToPage(page = targetPage)
            }
        }
    }
}

