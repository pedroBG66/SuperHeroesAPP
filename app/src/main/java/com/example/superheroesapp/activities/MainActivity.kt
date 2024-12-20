package com.example.superheroesapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.superheroesapp.R
import com.example.superheroesapp.utils.RetrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.util.Log
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.superheroesapp.adaters.SuperheroAdapter
import com.example.superheroesapp.data.SuperHero
import com.example.superheroesapp.databinding.ActivityDetailLayoutBinding
import com.example.superheroesapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    lateinit var adapter: SuperheroAdapter

    var superheroList: List<SuperHero> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = SuperheroAdapter(superheroList) { position ->
            val superhero = superheroList[position]
            navigateToDetail(superhero)
        }

        binding.recyclerViewLayout.adapter = adapter
        binding.recyclerViewLayout.layoutManager = GridLayoutManager(this, 2)

        searchSuperHeroes("a")
    }

    private fun navigateToDetail(superhero: SuperHero) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.EXTRA_SUPERHERO_ID, superhero.id)
        startActivity(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu_activity, menu)

        val menuItem = menu?.findItem(R.id.main_menu)!!
        val searchView= menuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchSuperHeroes(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })
        return true
    }

    fun searchSuperHeroes(query: String) {
        val service = RetrofitProvider.getRetrofit()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = service.findSuperHeroByName(query)

                CoroutineScope(Dispatchers.Main).launch {
                    if (result.response == "success") {
                        superheroList = result.results
                        adapter.updateItems(result.results)
                    } else {
                        // TODO: Mostrar mensaje de que no se ha encontrado nada
                    }
                }
            } catch (e: Exception) {
                Log.e("API", e.stackTraceToString())
            }
        }
    }


}
