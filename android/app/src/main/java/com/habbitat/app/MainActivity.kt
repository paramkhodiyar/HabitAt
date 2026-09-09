package com.habbitat.app

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
import com.habbitat.app.ui.calendar.CalendarViewModel
import com.habbitat.app.ui.calendar.CalendarViewModelFactory
import com.habbitat.app.ui.home.HomeViewModel
import com.habbitat.app.ui.home.HomeViewModelFactory
import com.habbitat.app.ui.navigation.FloatingBottomBar
import com.habbitat.app.ui.navigation.NavGraph
import com.habbitat.app.ui.theme.HabbitAtTheme
import com.habbitat.app.ui.theme.WarmIvory

class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory((application as HabbitAtApp).repository)
    }

    private val calendarViewModel: CalendarViewModel by viewModels {
        CalendarViewModelFactory((application as HabbitAtApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            enableEdgeToEdge()
        } catch (_: Throwable) {
            // Fallback for custom ROMs
        }

        setContent {
            HabbitAtTheme {
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
