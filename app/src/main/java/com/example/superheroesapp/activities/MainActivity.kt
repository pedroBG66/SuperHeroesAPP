package com.example.superheroesapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.superheroesapp.R
import com.example.superheroesapp.utils.RetrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val service = RetrofitProvider.getRetrofit()


        CoroutineScope(Dispatchers.IO).launch {
            val result = service.findSuperHeroByName("super")
            println(result)
        }
    }
}

