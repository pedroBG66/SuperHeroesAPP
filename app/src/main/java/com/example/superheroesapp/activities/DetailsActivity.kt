package com.example.superheroesapp.activities

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
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


class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SUPERHERO_ID = "SUPERHERO_ID"
    }

    lateinit var binding: ActivityDetailLayoutBinding

    lateinit var superhero: SuperHero

    private var isFrontVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.details_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getIntExtra(EXTRA_SUPERHERO_ID, -1).toString()

        getSuperhero(id)

        binding.card.setOnClickListener() {
            flipCard()
        }
    }

    fun loadData() {
        binding.superHeroNameTextView.text = superhero.name
        binding.realNameTextView.text =
            "Nombre real: ${superhero.biography.fullName ?: "Desconocido"}"
        binding.birthPlaceTextView.text =
            "Lugar de nacimiento: ${superhero.biography.placeBirth ?: "Desconocido"}"
        binding.firstAppearanceTextView.text =
            "Primera aparición: ${superhero.biography.firstAppearance ?: "Desconocida"}"


        binding.genderTextView.text = " ${superhero.appearance.gender ?: "Desconocido"}"
        binding.heightTextView.text = " ${superhero.appearance.height[1] ?: "Desconocida"}"
        binding.weightTextView.text = "${superhero.appearance.weight[1] ?: "Desconocido"}"
        binding.raceTextView.text = "${superhero.appearance.race ?: "Desconocida"}"

        binding.powerStatsTitle.text = "SUPER POWER STATS"
        binding.powerStatsTextView.text = "${superhero.powerStats.power ?: "Desconocida"}"
        binding.speedStatsTextView.text = "${superhero.powerStats.speed ?: "Desconocida"}"
        binding.strengthStatsTextView.text = "${superhero.powerStats.strength ?: "Desconocida"}"
        binding.durabilityStatsTextView.text = "${superhero.powerStats.durability ?: "Desconocida"}"
        binding.intelligenceStatsTextView.text = "${superhero.powerStats.intelligence ?: "Desconocida"}"
        binding.combatStatsTextView.text = "${superhero.powerStats.combat ?: "Desconocida"}"


        binding.avgPowerStats.text = avgStats(superhero)


        Picasso.get().load(superhero.urlImage.url).into(binding.avatarImageView)
        Picasso.get().load(superhero.urlImage.url).into(binding.avatarImageViewBackCard)

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

    private fun flipCard() {
        binding.card.animate()
            .rotationY(90f)
            .setDuration(300)
            .setInterpolator(DecelerateInterpolator())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    if (isFrontVisible) {
                        binding.frontCard.visibility = View.GONE
                        binding.backCard.visibility = View.VISIBLE
                    } else {
                        binding.backCard.visibility = View.GONE
                        binding.frontCard.visibility = View.VISIBLE
                    }
                    isFrontVisible = !isFrontVisible

                    binding.card.rotationY = -90f
                    binding.card.animate()
                        .rotationY(0f)
                        .setDuration(300)
                        .setInterpolator(DecelerateInterpolator())
                        .setListener(null)
                }
            })
    }

    fun avgStats(superhero: SuperHero): String {
        val stats = listOfNotNull(
            superhero.powerStats.power?.toIntOrNull(),
            superhero.powerStats.speed?.toIntOrNull(),
            superhero.powerStats.combat?.toIntOrNull(),
            superhero.powerStats.strength?.toIntOrNull(),
            superhero.powerStats.intelligence?.toIntOrNull(),
            superhero.powerStats.durability?.toIntOrNull()
        )
        return if (stats.isNotEmpty()) {
            (stats.sum() / stats.size).toString()
        } else {
            "unknown"
        }
    }
}
