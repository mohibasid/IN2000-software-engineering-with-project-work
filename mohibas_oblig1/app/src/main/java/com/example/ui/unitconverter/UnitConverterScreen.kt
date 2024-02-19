package com.example.mohibas_oblig1.ui.unitconverter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mohibas_oblig1.R
import kotlinx.coroutines.launch

@Composable
fun UnitConverterScreen(onNavigateToNext: () -> Unit) {

    Column (modifier = Modifier.padding(20.dp)) {
        Text(
            text = "UnitConverterScreen",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        UnitConverter()


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

private fun konverterTilLiter(antall: Double, unit: String): Double{
    return when (unit) {
        "Fluid Ounce" -> antall * 0.0295735
        "Cup" -> antall * 0.236588
        "Gallon" -> antall * 3.78541
        "Hogshead" -> antall * 238.481
        else -> antall
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitConverter() {
    var input by remember { mutableStateOf("") }
    var resultat by remember { mutableStateOf("") }
    val imperiskeEnheter = stringArrayResource(id = R.array.imperiske_enheter)
    var expanded by remember { mutableStateOf(false) }
    var valgtEnhet by remember { mutableStateOf(imperiskeEnheter[0]) }
    val focusManager = LocalFocusManager.current
    var showSnack by remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }),
            label = {
                Text(
                    text = stringResource(id = R.string.konverter)
                )
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(50.dp)
        ) {

            TextField(
                value = valgtEnhet,
                onValueChange = {},
                readOnly = true,
                label = { Text("Enhet") },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            ExposedDropdownMenu(expanded = expanded,
                onDismissRequest = { expanded = false }) {
                imperiskeEnheter.forEach { valAlt ->
                    DropdownMenuItem(
                        text = { Text(valAlt) },
                        onClick = {
                            valgtEnhet = valAlt
                            expanded = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                if (input.isEmpty() || input.toDoubleOrNull() == null) {
                    showSnack = true
                } else {
                    val inputNum = input.toDouble()
                    val converted = konverterTilLiter(inputNum, valgtEnhet)
                    resultat = "%.2f".format(converted)
                    focusManager.clearFocus()
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        {
            Text(text = "Konverter")
        }

        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        if (showSnack) {
            Scaffold(
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Feilmelding: Ugyldig eller tom input",
                                    withDismissAction = true,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    ) {
                        Text(text = "Error!")
                    }
                },
                content = { innerPadding ->
                    Text(
                        text = "",
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .wrapContentSize()
                    )
                }
            )
        }

        Spacer(modifier = Modifier.padding(35.dp))

        Text(
            text = "Liter: $resultat",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )


    }
}








