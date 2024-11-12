package com.example.superheroesapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.superheroesapp.R
import com.example.superheroesapp.data.HeroesResponse
import com.example.superheroesapp.utils.RetrofitProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.example.superheroesapp.adaters.SuperHeroAdapter
import com.example.superheroesapp.data.SuperHero
import com.example.superheroesapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var heroesResults: HeroesResponse
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: SuperHeroAdapter

    var superheroList: List<Superhero> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        adapter = SuperHeroAdapter(SuperHero)




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
        // Crear la instancia del servicio de Retrofit
        val service = RetrofitProvider.getRetrofit()

        // Manejo de excepciones para la corrutina
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Log.e("MainActivity", "Error en la corrutina", throwable)  // Usa Log.e para errores
        }

        // Lanzar la corrutina en Dispatchers.IO
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            try {
                // Llamada a la API para obtener resultados
                heroesResults = service.findSuperHeroByName("batman")

                // Volver al hilo principal para registrar en Logcat
                withContext(Dispatchers.Main) {
                    if (heroesResults.response == "success") {
                        Log.d("MainActivity", heroesResults.results[0].id)
                    } else {
                        Log.d("MainActivity", "No results found for the given name.")
                    }
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "error with the API", e)  // Imprime el error en Logcat
            }
        }
    }


}
