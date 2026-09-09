package com.habbitat.app.ui.navigation

import android.net.Uri
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.habbitat.app.ui.calendar.CalendarScreen
import com.habbitat.app.ui.calendar.CalendarViewModel
import com.habbitat.app.ui.home.HomeScreen
import com.habbitat.app.ui.home.HomeViewModel
import com.habbitat.app.ui.proof.CameraCaptureScreen
import com.habbitat.app.ui.proof.VerificationResultScreen
import com.habbitat.app.ui.settings.SettingsScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    calendarViewModel: CalendarViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val habits by homeViewModel.habits.collectAsState()

    // Smooth spring physics for screen transitions
    val springSpec = spring<Float>(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessMediumLow
    )

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier,
        enterTransition = {
            fadeIn(animationSpec = springSpec) + scaleIn(
                initialScale = 0.96f,
                animationSpec = springSpec
            )
        },
        exitTransition = {
            fadeOut(animationSpec = springSpec) + scaleOut(
                targetScale = 0.98f,
                animationSpec = springSpec
            )
        },
        popEnterTransition = {
            fadeIn(animationSpec = springSpec) + scaleIn(
                initialScale = 0.98f,
                animationSpec = springSpec
            )
        },
        popExitTransition = {
            fadeOut(animationSpec = springSpec) + scaleOut(
                targetScale = 0.96f,
                animationSpec = springSpec
            )
        }
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = homeViewModel,
                onOpenProofCapture = { habitId ->
                    navController.navigate("proof/$habitId")
                }
            )
        }
        composable(Screen.Calendar.route) {
            CalendarScreen(
                viewModel = calendarViewModel,
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onOpenCreateHabit = {
                    navController.navigate(Screen.Home.route)
                    homeViewModel.openCreateSheet()
                }
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = Screen.ProofCapture.route,
            arguments = listOf(navArgument("habitId") { type = NavType.LongType })
        ) { backStackEntry ->
            val habitId = backStackEntry.arguments?.getLong("habitId") ?: -1L
            val habit = habits.find { it.id == habitId }

            if (habit != null) {
                var capturedPhotoUri: Uri? by remember { mutableStateOf(null) }

                val currentUri = capturedPhotoUri
                if (currentUri == null) {
                    CameraCaptureScreen(
                        habit = habit,
                        onBack = { navController.popBackStack() },
                        onPhotoCaptured = { uri ->
                            capturedPhotoUri = uri
                        }
                    )
                } else {
                    VerificationResultScreen(
                        habit = habit,
                        imageUri = currentUri,
                        onAccept = { result ->
                            homeViewModel.processVerifiedProof(
                                context = context,
                                habitId = habit.id,
                                imageUri = currentUri.toString(),
                                verified = result.verified,
                                confidence = result.confidence
                            )
                            navController.popBackStack()
                        },
                        onRetake = {
                            capturedPhotoUri = null
                        }
                    )
                }
            }
        }
    }
}
