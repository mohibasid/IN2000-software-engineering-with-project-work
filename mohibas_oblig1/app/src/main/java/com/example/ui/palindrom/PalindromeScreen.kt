package com.example.mohibas_oblig1.ui.palindrom


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mohibas_oblig1.R



@Composable
fun PalindromeScreen(onNavigateToNext: () -> Unit){
    Column (modifier = Modifier.padding(20.dp)) {
        Text(
            text = "PalindromeScreen",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.padding(20.dp))

        PalindromeChecker()

        Spacer(modifier = Modifier.padding(70.dp))

        Button(onClick = {onNavigateToNext()}, modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth()

        ) {
            Text(
                text = stringResource(id = R.string.gå_neste),
            )
        }
    }
}


@Composable
fun PalindromeChecker() {
    var input by remember {mutableStateOf("")}
    var resultat by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = {
                Text(
                    text = stringResource(id = R.string.skriv_inn),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            modifier = Modifier
                .padding(50.dp)
                .align(Alignment.CenterHorizontally),

        )

        Spacer(modifier = Modifier.padding(3.dp))

        Button(onClick = {
            resultat =
                if (input.isPalindrome()) "Dette er en palindrome" else "Dette er ikke en palindrome"
            input = ""
            focusManager.clearFocus()
                         }, modifier = Modifier.align(Alignment.CenterHorizontally))
        {
            Text(
                text = stringResource(id = R.string.sjekk_tekst),
                fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.padding(30.dp))

        Text(
            text = resultat,
            fontWeight = FontWeight.Bold ,
            fontSize = 25.sp,
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.CenterHorizontally)
        )

    }

}

private fun String.isPalindrome(): Boolean {
    val ren = this.lowercase().filter { it.isLetterOrDigit() }
    return ren == ren.reversed()
}