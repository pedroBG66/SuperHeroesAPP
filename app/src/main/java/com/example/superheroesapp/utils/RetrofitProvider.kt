package com.example.superheroesapp.utils

import com.example.superheroesapp.services.HeroesService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {
    class RetrofitProvider {
        companion object {
            fun getRetrofit(): HeroesService {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://superheroapi.com/api/1cfa3f34920afcbb99746a02dddab240/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                return retrofit.create(HeroesService::class.java)
            }
        }
    }
}