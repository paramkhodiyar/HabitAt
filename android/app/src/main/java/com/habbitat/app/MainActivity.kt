package com.habitAt.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.habitAt.app.data.UserPreferences
import com.habitAt.app.ui.calendar.CalendarViewModel
import com.habitAt.app.ui.calendar.CalendarViewModelFactory
import com.habitAt.app.ui.home.HomeViewModel
import com.habitAt.app.ui.home.HomeViewModelFactory
import com.habitAt.app.ui.navigation.FloatingBottomBar
import com.habitAt.app.ui.navigation.NavGraph
import com.habitAt.app.ui.onboarding.OnboardingScreen
import com.habitAt.app.ui.theme.habitAtTheme
import com.habitAt.app.ui.theme.WarmIvory

class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory((application as habitAtApp).repository)
    }

    private val calendarViewModel: CalendarViewModel by viewModels {
        CalendarViewModelFactory((application as habitAtApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            enableEdgeToEdge()
        } catch (_: Throwable) {
            // Fallback for custom ROMs
        }

        val userPrefs = UserPreferences(this)

        setContent {
            habitAtTheme {
                var isOnboardingDone by remember { mutableStateOf(userPrefs.isOnboardingCompleted) }

                if (!isOnboardingDone) {
                    OnboardingScreen(
                        onOnboardingComplete = {
                            userPrefs.isOnboardingCompleted = true
                            isOnboardingDone = true
                        }
                    )
                } else {
                    val navController = rememberNavController()

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = WarmIvory,
                        bottomBar = {
                            FloatingBottomBar(navController = navController)
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.fillMaxSize()) {
                            NavGraph(
                                navController = navController,
                                homeViewModel = homeViewModel,
                                calendarViewModel = calendarViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
