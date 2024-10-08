package com.firman.submissionandroidpemula

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvCharacter: RecyclerView
    private val list = ArrayList<Character>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Karakter Genshin Impact"

        rvCharacter = findViewById(R.id.rv_character)
        rvCharacter.setHasFixedSize(true)
        list.addAll(getListCharacter())
        showRecyclerList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_list -> {
                rvCharacter.layoutManager = LinearLayoutManager(this)
            }
            R.id.action_grid -> {
                rvCharacter.layoutManager = GridLayoutManager(this, 2)
            }
            R.id.action_about -> {
                val moveIntent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(moveIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getListCharacter(): ArrayList<Character> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataElement = resources.getStringArray(R.array.data_element)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataBackground = resources.obtainTypedArray(R.array.data_background)

        val listCharacter = ArrayList<Character>()

        val size = minOf(dataName.size, dataDescription.size, dataElement.size, dataPhoto.length())

        for (i in 0 until size) {
            val friendIndices = when (i) {
                0 -> intArrayOf(1, 2, 3)
                1 -> intArrayOf(0, 4, 5)
                2 -> intArrayOf(0, 3, 6)
                3 -> intArrayOf(0, 2, 4)
                4 -> intArrayOf(1, 5, 6)
                5 -> intArrayOf(1, 4, 7)
                6 -> intArrayOf(2, 4, 8)
                7 -> intArrayOf(5, 6, 9)
                8 -> intArrayOf(6, 9, 3)
                9 -> intArrayOf(5, 7, 8)
                else -> intArrayOf()
            }

            val character = Character(
                name = dataName[i],
                description = dataDescription[i],
                photo = dataPhoto.getResourceId(i, -1),
                backgroundIndex = i,
                friendIndices = friendIndices,
                element = dataElement[i]
            )

            if (character.photo != -1) {
                listCharacter.add(character)
            }
        }

        dataPhoto.recycle()
        dataBackground.recycle()
        return listCharacter
    }


    private fun showRecyclerList() {
        rvCharacter.layoutManager = LinearLayoutManager(this)
        val listCharacterAdapter = ListCharacterAdapter(list)
        rvCharacter.adapter = listCharacterAdapter

        listCharacterAdapter.setOnItemClickCallback(object : ListCharacterAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Character) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_CHARACTER, data)
                startActivity(intent)
                showSelectedCharacter(data)
            }
        })
    }

    private fun showSelectedCharacter(character: Character) {
        Toast.makeText(this, "Kamu memilih " + character.name, Toast.LENGTH_SHORT).show()
    }
}
