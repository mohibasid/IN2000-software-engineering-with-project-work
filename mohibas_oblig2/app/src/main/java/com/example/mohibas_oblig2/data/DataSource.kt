package com.example.mohibas_oblig2.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*

class DataSource (private val path: String) {

    //Lager instans av HttpClient for å bruke i senere funksjoner
    private val client = HttpClient{
        install(ContentNegotiation){
            gson{
                setPrettyPrinting()
            }
        }
    }

    //Gjør get-request og henter alpakkapartier
    suspend fun getAlpacaParties(): List<AlpacaParty>{

        val alpacaParties: AlpacaParties = client.get(path).body()
        return alpacaParties.alpacaParties
    }

}

class StemmeDataSource(private val path: String){

    //Lager instans av HttpClient for å bruke i senere funksjoner
    private val client = HttpClient{
        install(ContentNegotiation){
            gson{
                setPrettyPrinting()
            }
        }
    }

    //Gjør get-request og henter stemme
    suspend fun getStemmer(): List<StemmeJson> {

        return client.get(path).body()
    }
}

class StemmeDataSourceDis3(private val path: String){

    //Lager instans av HttpClient for å bruke i senere funksjoner
    private val client = HttpClient{
        install(ContentNegotiation){
            gson{
                setPrettyPrinting()
            }
        }
    }

    //Gjør get-request og henter stemmer fra XML
    suspend fun getStemmer(): List<StemmeXml> {
        return ParseXML().parse(client.get(path).body())
    }
}
