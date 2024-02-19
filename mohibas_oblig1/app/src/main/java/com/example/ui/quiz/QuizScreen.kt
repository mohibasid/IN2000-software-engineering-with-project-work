package com.example.mohibas_oblig1.ui.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class QuizUiState(val spmListe: List<Question>, var riktigBesvart: Int = 0)

data class Question (
    val spm: String,
    val svar: Boolean
)

val spoersmal = listOf(
    Question("Hotmail kom ut i 1996", true),
    Question("Den sterkeste muskelen i kroppen er tungen", true),
    Question("Jorda er flat", false),
    Question("Jupiter er den femte planeten fra solen", true)
)


@Composable
fun QuizScreen() {

    Column (modifier = Modifier.padding(20.dp)) {
        Text(
            text = "QuizScreen",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 30.sp,
        )

        Quiz(quizUiState = QuizUiState(spoersmal))
    }

}

@Composable
fun Quiz(quizUiState: QuizUiState) {
    var spmIndeks by remember { mutableStateOf(0) }
    var navarendeSpm by remember { mutableStateOf(quizUiState.spmListe[spmIndeks]) }
    var quizFerdig by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Spacer(modifier = Modifier.padding(20.dp))
        Text(
            text = navarendeSpm.spm,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        if (spmIndeks < quizUiState.spmListe.size) {

            Spacer(modifier = Modifier.padding(30.dp))

            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Button(
                    onClick = {
                        if (navarendeSpm.svar) {
                            quizUiState.riktigBesvart++
                        }
                        spmIndeks = spmIndeks++
                        navarendeSpm = quizUiState.spmListe[spmIndeks]

                        if (spmIndeks == quizUiState.spmListe.size){
                            quizFerdig = true
                        }
                    },
                    modifier = Modifier
                        .background(color = Color.Green)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = "Fakta",
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        if (!navarendeSpm.svar) {
                            quizUiState.riktigBesvart++
                        }
                        spmIndeks = spmIndeks++
                        navarendeSpm = quizUiState.spmListe[spmIndeks]

                        if (spmIndeks == quizUiState.spmListe.size){
                            quizFerdig = true
                        }
                    },
                    modifier = Modifier
                        .background(Color.Red)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(text = "Fleip")
                }
            }

            Spacer(modifier = Modifier.padding(40.dp))

            if (quizFerdig) {
                Text(
                    text = "Quizen er ferdig. Antall Poeng: ${quizUiState.riktigBesvart}/4",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            else{
                Text(text = "Antall poeng: ${quizUiState.riktigBesvart}/4", textAlign = TextAlign.Companion.Center)
            }

            Spacer(modifier = Modifier.padding(40.dp))

            Button(
                onClick = {
                    quizFerdig = false
                    quizUiState.riktigBesvart = 0
                    spmIndeks = 0
                    navarendeSpm = quizUiState.spmListe[spmIndeks]
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            ) {
                Text(text = "Start quiz på nytt")
            }
        }
    }
}

