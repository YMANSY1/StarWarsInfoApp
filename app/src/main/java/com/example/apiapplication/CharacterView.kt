package com.example.apiapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.apiapplication.databinding.ActivityCharacterViewBinding
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class CharacterView : AppCompatActivity() {
    private lateinit var binding: ActivityCharacterViewBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var films: MutableList<String>
        var species:MutableList<String>
        var starships:MutableList<String>
        var vehichles:MutableList<String>
        val intent = intent
        val character = intent.getParcelableExtra<ResultSW>("name")
        binding.characterName.text = character?.name
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                binding.progressBar2.isVisible = true

                val homeworldText = withContext(Dispatchers.IO) { URL(character?.homeworld).readText() }
                val jsonElement = JsonParser.parseString(homeworldText)
                val homeworld = jsonElement.asJsonObject.get("name").asString

                val films = getDataFromURL(character?.films ?: emptyList(), "title")
                val starships = getDataFromURL(character?.starships ?: emptyList(), "model")
                val vehicles = getDataFromURL(character?.vehicles ?: emptyList(), "name")

                binding.progressBar2.isVisible = false

                binding.attributesText.text = """
            Gender= ${character?.gender}     
            Hair Color= ${character?.hair_color}
            Birth Date= ${character?.birth_year}                 
            Height= ${character?.height}
            Homeworld= $homeworld
            Skin Colour= ${character?.skin_color}
            Weight= ${character?.mass}
            Films= ${films.joinToString(", ")}
            Starships= ${if (starships.isEmpty()) "None" else starships.joinToString(", ")}
            Vehicles= ${if (vehicles.isEmpty()) "None" else vehicles.joinToString(", ")}
        """.trimIndent()
            }
        }

    }

    private suspend fun getDataFromURL(urls: List<String>, category:String):MutableList<String>{
        val data= mutableListOf<String>()
        var text:String
        for (url in urls){
            text= withContext(Dispatchers.IO){ URL(url).readText()}
            val jsonElement= JsonParser.parseString(text)
            data.add(jsonElement.asJsonObject.get(category).asString)
        }
        return data
    }

}