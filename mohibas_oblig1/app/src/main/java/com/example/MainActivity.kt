package com.example.mohibas_oblig1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mohibas_oblig1.ui.palindrom.PalindromeScreen
import com.example.mohibas_oblig1.ui.quiz.QuizScreen
import com.example.mohibas_oblig1.ui.quiz.QuizUiState
import com.example.mohibas_oblig1.ui.theme.Mohibas_oblig1Theme
import com.example.mohibas_oblig1.ui.unitconverter.UnitConverterScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Mohibas_oblig1Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MultipleScreenApp()
                }
            }
        }
    }
}


@Composable
fun MultipleScreenApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "palindrome") {
        composable("palindrome") { PalindromeScreen(onNavigateToNext = { navController.navigate("unitConverter")}) }
        composable("unitConverter") { UnitConverterScreen(onNavigateToNext = { navController.navigate("quiz")}) }
        composable("quiz") { QuizScreen()}

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Mohibas_oblig1Theme {
    }
}