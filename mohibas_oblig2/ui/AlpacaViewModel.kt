package com.example.mohibas_oblig2.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mohibas_oblig2.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class AlpacaViewModel : ViewModel() {

    private val dataSource = DataSource("https://in2000-proxy.ifi.uio.no/alpacaapi/alpacaparties")
    private val dataSourceDistrikt1 =
        StemmeDataSource("https://in2000-proxy.ifi.uio.no/alpacaapi/district1")
    private val dataSourceDistrtikt2 =
        StemmeDataSource("https://in2000-proxy.ifi.uio.no/alpacaapi/district2")
    private val dataSourceDistrikt3 =
        StemmeDataSourceDis3("https://in2000-proxy.ifi.uio.no/alpacaapi/district3")


    private var distrikt by mutableStateOf("")
    private val _uiState =
        MutableStateFlow(AlpacaUiState(alpaca = listOf(), valgtDistrikt = distrikt))
    val uiState: StateFlow<AlpacaUiState> = _uiState.asStateFlow()
    private val _stemmeUiState =
        MutableStateFlow(StemmeUiState(mutableListOf(), mutableListOf(), mutableListOf()))
    val stemmeUiState: StateFlow<StemmeUiState> =
        _stemmeUiState.asStateFlow()

    init {
        viewModelScope.launch {
            alpakkaObjekter()
            loadStemmer(dataSourceDistrikt1, dataSourceDistrtikt2, dataSourceDistrikt3)
        }

    }

    private fun alpakkaObjekter() {
        //hentAlpacaParty er en suspend-funksjon, så må kalle det fra en coroutine
        //Gir MutableStateFlow-objektet ny verdi
        viewModelScope.launch(Dispatchers.IO) {
            val alpacaPartier = dataSource.getAlpacaParties()
            _uiState.value = AlpacaUiState(alpaca = alpacaPartier, "")
        }
    }

    private fun loadStemmer(
        dataSourceNr1: StemmeDataSource,
        dataSourceNr2: StemmeDataSource,
        dataSourceNr3: StemmeDataSourceDis3
    ) {
        viewModelScope.launch{
            val dis1 = dataSourceNr1.getStemmer()
            val dis2 = dataSourceNr2.getStemmer()
            val dis3 = dataSourceNr3.getStemmer()
            _stemmeUiState.value = StemmeUiState(dis1, dis2, dis3)
        }
    }

    fun byttDis(valgtDis: String) {
        _uiState.value = uiState.value.copy(valgtDistrikt = valgtDis)
    }

    fun convertListToMapXml(distrikt3Stemmer: List<StemmeXml>): Map<String, Int?>{
        return distrikt3Stemmer.associateBy ({it.id.toString()},{it.stemmer})
    }

    fun countVotes(stemmeListe: List<StemmeJson>): Map<String, Int> {
        return stemmeListe.groupingBy { it.id }.eachCount()
    }
}


