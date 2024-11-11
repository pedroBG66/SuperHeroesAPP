package com.example.superheroesapp.data
import com.google.gson.annotations.SerializedName


//aqui establecemos las respuestas de la api en base al json que tenemos en la doc de la api
data class HeroesResponse (
    @SerializedName ("response") val response: String,
    @SerializedName ("results-for") val resultsFor: String,
    @SerializedName ("results") val results: List<SuperHero>
){}

//como results es un array de objetos hay que crear una clase que contenga esos datos

    data class SuperHero(
        @SerializedName ("id") val id: String,
        @SerializedName ("name") val name: String,)
    { }

