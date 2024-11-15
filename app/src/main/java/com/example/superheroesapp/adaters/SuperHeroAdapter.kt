package com.example.superheroesapp.adaters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.superheroesapp.data.SuperHero
import com.example.superheroesapp.databinding.ItemSuperheroBinding
import com.squareup.picasso.Picasso


class SuperheroAdapter(var items: List<SuperHero>, val onItemClick:  (Int) -> Unit) : RecyclerView.Adapter<ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val superhero = items[position]
        holder.render(superhero)
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSuperheroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(items: List<SuperHero>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class ViewHolder(val binding: ItemSuperheroBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(superhero: SuperHero) {
        binding.superHeroNameTextView.text = superhero.name
        Picasso.get().load(superhero.urlImage.url).into(binding.avatarImageView)
    }
}