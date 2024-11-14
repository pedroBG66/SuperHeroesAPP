package com.example.superheroesapp.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.superheroesapp.R
import com.example.superheroesapp.data.SuperHero
import com.example.superheroesapp.databinding.ActivityDetailLayoutBinding
import com.example.superheroesapp.utils.RetrofitProvider
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DestailsActivity : AppCompatActivity() {

    companion object {
         const val EXTRA_SUPERHERO_ID = "SUPERHERO_ID"
    }

    lateinit var binding: ActivityDetailLayoutBinding

    lateinit var superhero: SuperHero

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getStringExtra(EXTRA_SUPERHERO_ID)!!

        getSuperhero(id)
    }

    fun loadData() {
        supportActionBar?.title = superhero.name
        Picasso.get().load(superhero.urlImage.url).into(binding.avatarImageView)

    }

    fun getSuperhero(id: String) {
        val service = RetrofitProvider.getRetrofit()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                superhero = service.findSuperHeroById(id)
                CoroutineScope(Dispatchers.Main).launch {
                    loadData()
                }
            } catch (e: Exception) {
                Log.e("API", e.stackTraceToString())
            }

        }
    }
}
