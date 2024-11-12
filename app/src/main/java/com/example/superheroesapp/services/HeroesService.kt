package com.example.superheroesapp.services

import com.example.superheroesapp.data.HeroesResponse
import com.example.superheroesapp.data.PowerStats
import com.example.superheroesapp.data.SuperHero
import retrofit2.http.GET
import retrofit2.http.Path



//en este apartado establecemos los parametros de busqueda en la api

interface HeroesService {
    @GET("search/{name}")
    suspend fun findSuperHeroByName (@Path("name") query: String) : HeroesResponse

    @GET("{character-id}")
    suspend fun findSuperHeroById (@Path("character-id") id: String) : SuperHero

    @GET("{id}/powerstats")
    suspend fun findStats (@Path("id") id: String) : PowerStats

}