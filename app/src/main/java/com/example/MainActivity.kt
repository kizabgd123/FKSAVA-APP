package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.BottomNavBar
import com.example.ui.screens.BookingScreen
import com.example.ui.screens.CafeScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.MyBookingsScreen
import com.example.ui.screens.SchoolScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.PitchNightBase
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.BookingViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp(
    viewModel: BookingViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    // Toast and Snackbar feedback
    LaunchedEffect(uiState.toastMessage) {
        uiState.toastMessage?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            viewModel.clearToast()
        }
    }

    // Back button handling
    BackHandler(enabled = true) {
        if (uiState.isConfirmationSheetOpen) {
            viewModel.closeConfirmationSheet()
        } else {
            when (uiState.currentScreen) {
                AppScreen.LOGIN -> viewModel.navigateTo(AppScreen.SPLASH)
                AppScreen.SKOLICA,
                AppScreen.KAFIC,
                AppScreen.MOJI_TERMINI -> viewModel.navigateTo(AppScreen.REZERVISI)
                AppScreen.REZERVISI -> viewModel.navigateTo(AppScreen.LOGIN)
                AppScreen.SPLASH -> {
                    // Standard exit
                }
            }
        }
    }

    val showBottomNav = uiState.currentScreen != AppScreen.SPLASH && uiState.currentScreen != AppScreen.LOGIN

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(PitchNightBase),
        bottomBar = {
            if (showBottomNav) {
                BottomNavBar(
                    currentScreen = uiState.currentScreen,
                    onTabSelected = { screen -> viewModel.navigateTo(screen) }
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = PitchNightBase
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = if (showBottomNav) innerPadding.calculateBottomPadding() else androidx.compose.ui.unit.Dp.Unspecified
                )
        ) {
            AnimatedContent(
                targetState = uiState.currentScreen,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "ScreenTransition"
            ) { targetScreen ->
                when (targetScreen) {
                    AppScreen.SPLASH -> {
                        SplashScreen(
                            viewModel = viewModel,
                            onNavigateToLogin = { viewModel.navigateTo(AppScreen.LOGIN) }
                        )
                    }
                    AppScreen.LOGIN -> {
                        LoginScreen(
                            viewModel = viewModel,
                            onNavigateToSplash = { viewModel.navigateTo(AppScreen.SPLASH) }
                        )
                    }
                    AppScreen.REZERVISI -> {
                        BookingScreen(viewModel = viewModel)
                    }
                    AppScreen.SKOLICA -> {
                        SchoolScreen(viewModel = viewModel)
                    }
                    AppScreen.KAFIC -> {
                        CafeScreen(viewModel = viewModel)
                    }
                    AppScreen.MOJI_TERMINI -> {
                        MyBookingsScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}
