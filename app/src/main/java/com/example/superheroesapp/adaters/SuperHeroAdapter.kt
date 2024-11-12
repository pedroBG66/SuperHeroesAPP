package com.example.superheroesapp.adaters

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.example.superheroesapp.data.SuperHero

class SuperHeroAdapter (var items : List<SuperHero>) : RecyclerView.adapter<ViewHolder> () {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val superhero = items[position]
        holder.render(superhero)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSuperheroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(items: List<Superhero>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class ViewHolder (val binding: ViewBinding)