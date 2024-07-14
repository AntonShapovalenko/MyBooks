package com.books.app.ui.details

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.books.app.R
import com.books.app.data.model.Book
import com.books.app.data.model.GenreType
import com.books.app.ui.common.title
import com.books.app.ui.theme.Blackberry
import com.books.app.ui.theme.CharcoalGray
import com.books.app.ui.theme.DustyLilac
import com.books.app.ui.theme.MagentaPink
import com.books.app.ui.theme.NunitoSans
import kotlin.math.absoluteValue


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(booksId: Int, onBackPress: () -> Unit) {

    val viewModel =
        hiltViewModel<DetailsViewModel, DetailsViewModel.DetailViewModelFactory> { factory ->
            factory.create(selectedBooksId = booksId)
        }

    val detailsScreenState by viewModel.detailsScreenState.collectAsState()
    DetailsScreenContent(detailsState = detailsScreenState, onBackPress = onBackPress)
}

@Composable
private fun DetailsScreenContent(detailsState: DetailsState, onBackPress: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {

        if (detailsState.isFailure) {
            // Show Failed Screen here
        } else if (detailsState.isLoading) {
            // Show Loading Screen here
        } else {
            val booksData = detailsState.books
            val booksYouWillLike = detailsState.booksYouWillLike

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                item("topBannerSlides") {
                    TopBannerSlidesContent(
                        booksData = booksData,
                        initialPage = detailsState.selectedBooksIndex,
                        onBackPress = onBackPress
                    )
                }
                item("booksYouWillLike") {
                    BooksLikesRow(booksYouWillLike)
                }
            }
            ReadNowButton()
        }
    }
}

@Composable
private fun TopBannerSlidesContent(
    booksData: List<Book>,
    initialPage: Int,
    onBackPress: () -> Unit
) {

    val pageCount = booksData.size
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        initialPageOffsetFraction = 0f,
        pageCount = { pageCount },
    )
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.details_background),
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize()
                .height(420.dp),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(460.dp)
        ) {
            BackButton(modifier = Modifier.statusBarsPadding(), onBackPress = onBackPress)

            val screenWidth = LocalConfiguration.current.screenWidthDp.dp
            val horizontalPadding = remember { (screenWidth - 200.dp) / 2 }
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = pagerState,
                beyondViewportPageCount = 1,
                pageSize = PageSize.Fixed(200.dp),
                contentPadding = PaddingValues(horizontal = horizontalPadding)
            ) { index ->
                booksData.getOrNull(index)?.let {
                    Image(
                        modifier = Modifier
                            .height(250.dp)
                            .graphicsLayer {
                                // Calculate the absolute offset for the current page from the
                                // scroll position. We use the absolute value which allows us to mirror
                                // any effects for both directions
                                val pageOffset = (
                                        (pagerState.currentPage - index) + pagerState
                                            .currentPageOffsetFraction
                                        ).absoluteValue

                                // We animate the alpha, between 50% and 100%
                                alpha = lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                                val scale = lerp(1f, 0.75f, pageOffset)
                                // apply the scale equally to both X and Y, to not distort the image
                                scaleX = scale
                                scaleY = scale
                            }
                            .clip(RoundedCornerShape(16.dp)),
                        painter = rememberAsyncImagePainter(it.coverUrl),
                        contentDescription = "Image banner",
                        contentScale = ContentScale.FillHeight,
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            NameBooksContent(
                name = booksData[pagerState.currentPage].name,
                author = booksData[pagerState.currentPage].author
            )
            Spacer(modifier = Modifier.height(8.dp))
            DetailsBookInfoRow(book = booksData[pagerState.currentPage])
        }
    }
    SummaryContent(summary = booksData[pagerState.currentPage].summary)
}

@Composable
private fun NameBooksContent(name: String, author: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            fontSize = 20.sp,
            fontFamily = NunitoSans,
            fontWeight = FontWeight.W700,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = author,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = NunitoSans,
            fontWeight = FontWeight.W700
        )
    }
}

@Composable
private fun SummaryContent(summary: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.summary),
            fontSize = 20.sp,
            color = Blackberry,
            fontWeight = FontWeight.W700
        )
        Text(
            summary,
            fontSize = 14.sp,
            color = CharcoalGray,
            fontWeight = W600
        )
        HorizontalLine()
    }
}

@Composable
private fun DetailsBookInfoRow(book: Book) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .padding(top = 32.dp, end = 16.dp, start = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            DetailsBookInfoItem(
                countInfo = book.views,
                typeInfo = stringResource(id = R.string.readers)
            )
            DetailsBookInfoItem(
                countInfo = book.likes,
                typeInfo = stringResource(id = R.string.likes)
            )
            DetailsBookInfoItem(
                countInfo = book.quotes,
                typeInfo = stringResource(id = R.string.quotes)
            )
            DetailsBookInfoItem(
                countInfo = book.genre.title(),
                typeInfo = stringResource(id = R.string.genre)
            )
        }
        HorizontalLine()
    }
}


@Composable
private fun DetailsBookInfoItem(countInfo: String, typeInfo: String) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            countInfo,
            fontSize = 18.sp,
            color = Blackberry,
            letterSpacing = 0.sp,
            lineHeight = 18.sp,
            fontWeight = FontWeight.W700
        )
        Text(
            typeInfo,
            fontSize = 12.sp,
            color = DustyLilac,
            letterSpacing = 0.sp,
            lineHeight = 12.sp,
            fontWeight = W600
        )
    }
}

@Composable
private fun HorizontalLine() {
    Spacer(modifier = Modifier.height(12.dp))
    Box(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(color = DustyLilac)
    )
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
private fun ReadNowButton() {
    Button(
        onClick = { /* Handle read now action */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 32.dp, top = 8.dp)
            .height(48.dp),

        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MagentaPink),

        ) {
        Text("Read Now", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.W800)
    }
}


@Composable
private fun BackButton(modifier: Modifier = Modifier, onBackPress: () -> Unit) {
    IconButton(
        onClick = {
            onBackPress()
        }, modifier = modifier

    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            tint = Color.White,
            contentDescription = "Button Description"
        )
    }
}

@Composable
private fun BooksLikesRow(bookData: List<Book>) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            modifier = Modifier, text = stringResource(id = R.string.you_will_like),
            fontSize = 20.sp,
            fontWeight = FontWeight.W700,
            color = Blackberry

        )
        LazyRow(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(items = bookData, key = { it.id }) { book ->
                BookItem(book)
            }
        }
    }
}

@Composable
private fun BookItem(book: Book) {
    Column(
        Modifier
            .width(120.dp)
    ) {
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .height(150.dp)
                .fillMaxWidth()
                .background(Color.Gray, shape = RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            painter = rememberAsyncImagePainter(book.coverUrl),
            contentDescription = "Image book cover"
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = book.name,
            maxLines = 2,
            fontWeight = W600,
            color = CharcoalGray,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Preview
@Composable
fun PreviewBookDetailsScreen() {
    val book = Book(
        id = 1,
        name = "If It's Only Love",
        author = "Zoey Evers",
        summary = "According to researchers at Duke University, habits account for about 40 percent of our behaviors on any given day. Your life today is essentially the sum of your habits. How in shape or out of shape you are? A result of your habits. How happy or unhappy you are? A result of your habits. How successful or unsuccessful you are? A result of your habits.",
        genre = GenreType.Romance,
        coverUrl = "https://via.placeholder.com/150",
        views = "22.2k",
        likes = "10.4k",
        quotes = "32.5k"
    )
}