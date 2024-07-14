package com.books.app.ui

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.books.app.ui.common.ZeroWindowInsets
import com.books.app.ui.details.DetailsScreen
import com.books.app.ui.main.MainScreen
import com.books.app.ui.splash.SplashScreenContent
import com.books.app.ui.theme.MyBooksTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.BLACK)
        )
        setContent {
            MyBooksTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        contentWindowInsets = ZeroWindowInsets
                    ) { padding ->

                        val navController = rememberNavController()
                        NavHost(
                            modifier = Modifier.padding(padding),
                            navController = navController,
                            startDestination = ScreenSplash
                        ) {
                            composable<ScreenSplash> {
                                SplashScreenContent(onDone = {
                                    navController.navigate(ScreenMain) {
                                        popUpTo(ScreenSplash) {
                                            inclusive = true
                                        }
                                    }
                                })
                            }
                            composable<ScreenMain> {
                                MainScreen(onOpenDetails = { id ->
                                    navController.navigate(
                                        ScreenDetails(
                                            id = id
                                        )
                                    )
                                })
                            }
                            composable<ScreenDetails> { navBackStackEntry ->
                                val args = navBackStackEntry.toRoute<ScreenDetails>()
                                DetailsScreen(
                                    booksId = args.id,
                                    onBackPress = { navController.popBackStack() })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Serializable
object ScreenSplash

@Serializable
object ScreenMain

@Serializable
data class ScreenDetails(val id: Int)