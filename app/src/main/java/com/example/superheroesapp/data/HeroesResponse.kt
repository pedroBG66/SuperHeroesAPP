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
        @SerializedName ("name") val name: String,
        @SerializedName("powerstats") val powerStats: PowerStats,
        @SerializedName("biography") val biography: Biography,
        @SerializedName("appearance") val appearance: Appearance,
        @SerializedName("work") val work: Work,
        @SerializedName("connections") val connections: Connections,
        @SerializedName("image") val urlImage: UrlImage,

){}
data class PowerStats(
    @SerializedName("intelligence") val intelligence:Int,
    @SerializedName("strength") val strength:Int,
    @SerializedName("speed") val speed:Int,
    @SerializedName("durability") val durability:Int,
    @SerializedName("power") val power:Int,
    @SerializedName("combat") val combat:Int
)
data class Biography(
    @SerializedName("full-name") val fullName:String,
    @SerializedName("alter-egos") val alterEgo:String,
    @SerializedName("aliases") val alias:List<String>,
    @SerializedName("place-of-birth") val placeBirth:String,
    @SerializedName("first-appearance") val firstAppearance:String,
    @SerializedName("publisher") val publisher:String,
    @SerializedName("alignment") val alignment:String

)
data class Appearance (
    @SerializedName("gender") val gender:String,
    @SerializedName("race") val race:String,
    @SerializedName("height") val height:List<String>,
    @SerializedName("weight") val weight:List<String>,
    @SerializedName("eyes-color") val eyesColor:String,
    @SerializedName("hair-color") val hairColor:String
)
data class Work(
    @SerializedName("ocuppation") val ocuppation:String,
    @SerializedName("base") val base:String
)
data class Connections(
    @SerializedName("group") val group:String,
    @SerializedName("affiliation") val affiliation:String,
    @SerializedName("relatives") val relatives:String
)
data class UrlImage(
    @SerializedName("url") val url:String
)


