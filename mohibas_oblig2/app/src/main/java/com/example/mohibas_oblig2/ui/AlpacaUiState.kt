package com.example.mohibas_oblig2.ui

import com.example.mohibas_oblig2.data.*

data class AlpacaUiState(
    val alpaca: List<AlpacaParty>,
    val valgtDistrikt: String
)

data class StemmeUiState(
    val stemmer1: List<StemmeJson>,
    val stemmer2: List<StemmeJson>,
    val stemmer3: List<StemmeXml>
)