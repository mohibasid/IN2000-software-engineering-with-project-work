package com.example.mohibas_oblig2.ui

import android.graphics.Color.parseColor
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.mohibas_oblig2.data.AlpacaParty
import com.example.mohibas_oblig2.data.StemmeJson
import com.example.mohibas_oblig2.data.StemmeXml


@RequiresApi(Build.VERSION_CODES.N)
@Composable
    fun AlpacaScreen(alpacaViewModel: AlpacaViewModel = viewModel()) {

        val alpacaUIState by alpacaViewModel.uiState.collectAsState()

        Column{
            DropDown(alpacaUiState = alpacaUIState, viewModel = alpacaViewModel)


            LazyColumn {
                items(alpacaUIState.alpaca) { parti ->
                    AlpacaCard(alpacaViewModel, parti)
                }
            }
        }
}



    @RequiresApi(Build.VERSION_CODES.N)
    @Composable
    fun AlpacaCard(viewModel: AlpacaViewModel, parti: AlpacaParty) {
        val alpacaUIState by viewModel.uiState.collectAsState()
        val stemmeUiState by viewModel.stemmeUiState.collectAsState()
        val votes1: List<StemmeJson> = stemmeUiState.stemmer1
        val map1 = viewModel.countVotes(votes1)
        val votes2: List<StemmeJson> = stemmeUiState.stemmer2
        val map2 = viewModel.countVotes(votes2)
        val votes3: List<StemmeXml> = stemmeUiState.stemmer3
        val map3Xml = viewModel.convertListToMapXml(votes3)
        var result by remember { mutableStateOf("") }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            ) {

            //Partifarge
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color(parseColor(parti.color)))
                    .align(Alignment.CenterHorizontally)
                    .wrapContentHeight(Alignment.Top)
            )

            //Text nr.1
            Text(
                text = parti.name,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )

            //Bilde
            AsyncImage(
                model = parti.img,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(115.dp)
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .align(Alignment.CenterHorizontally)
            )

            //Text nr.2
            Text(
                text = "Leader: ${parti.leader}",
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodySmall,
                fontSize = 18.sp
            )

            val district = when (alpacaUIState.valgtDistrikt) {
                "Distrikt nr.1" -> Triple(map1, votes1, map1)
                "Distrikt nr.2" -> Triple(map2, votes2, map2)
                "Distrikt nr.3" -> Triple(map3Xml, votes3, map3Xml)
                else -> null
            }


            if (district != null && district.second.isNotEmpty()) {
                val voteCount = district.first.getOrDefault(parti.id, 0)
                val votePercentage = voteCount !!/ district.second.size.toFloat() * 100
                val formattedPercentage = String.format("%.1f", votePercentage)
                result = "Antall stemmer: $voteCount\n Antall prosent: $formattedPercentage%"
            }


            //Appen viser riktig antall stemmer og prosent for distrikt 1 og 2, men ikke 3.
            Text(text = result)

        }
    }




@OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DropDown(alpacaUiState: AlpacaUiState, viewModel: AlpacaViewModel){
        var expanded by remember {
            mutableStateOf(false)
        }
        val valgtDistrikt = listOf("Distrikt nr.1", "Distrikt nr.2", "Distrikt nr.3")
        var valgtAltText by remember {
            mutableStateOf("")
        }

        ExposedDropdownMenuBox(
            expanded = expanded ,
            onExpandedChange = {expanded = !expanded},
            modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally)
            ) {

            TextField(
                value = valgtAltText,
                onValueChange = {valgtAltText = it},
                modifier = Modifier
                    .menuAnchor()
                    .padding(32.dp),
                readOnly = true,
                label = { Text(text = "Velg distrikt")},
                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {expanded = false}) {
                    valgtDistrikt.forEach{ valgt ->
                        DropdownMenuItem(
                            text = { Text(text = valgt)},
                            onClick = {
                                valgtAltText = valgt
                                expanded = false
                                viewModel.byttDis(valgt) },
                            modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )

            }
        }
    }
}

