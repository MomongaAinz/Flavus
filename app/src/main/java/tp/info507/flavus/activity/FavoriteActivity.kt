package tp.info507.flavus.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import tp.info507.flavus.R
import tp.info507.flavus.adapter.FavoriteRecipeAdapter
import tp.info507.flavus.model.Recipe
import tp.info507.flavus.storage.FavorisStorage

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favoriteList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        favoriteList = findViewById(R.id.favorite_list)
        favoriteList.layoutManager = GridLayoutManager(this, 3)

        loadFavorites()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.navigation_favorites

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_main -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.navigation_favorites -> true
                else -> false
            }
        }
    }

    private fun loadFavorites() {
        // Récupérer les recettes favorites à partir du stockage
        val favorites: List<Recipe> = FavorisStorage.getFavorites(applicationContext)

        // Vérifier si la liste des favoris est vide
        if (favorites.isEmpty()) {
            Toast.makeText(this, "Aucune recette favorite trouvée.", Toast.LENGTH_SHORT).show()
        }

        // Configuration de l'adaptateur pour afficher les recettes favorites
        favoriteList.adapter = FavoriteRecipeAdapter(this, favorites) { recipe ->
            // Log pour vérifier la recette cliquée
            Log.d("FavoriteActivity", "Recipe clicked: ${recipe.nom}, ID: ${recipe.id}")

            // Lancer RecipeActivity pour afficher les détails de la recette
            val intent = Intent(this, FavoriteRecipeActivity::class.java).apply {
                putExtra(MainActivity.EXTRA_MEAL, recipe.id)
            }
            startActivity(intent)
        }
    }
}
