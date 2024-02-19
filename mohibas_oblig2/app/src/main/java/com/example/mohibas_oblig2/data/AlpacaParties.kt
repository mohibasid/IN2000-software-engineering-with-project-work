package com.example.mohibas_oblig2.data

import com.google.gson.annotations.SerializedName

data class AlpacaParties(
    @SerializedName("parties" )
    var alpacaParties : ArrayList<AlpacaParty> = arrayListOf()
)