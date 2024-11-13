package tp.info507.flavus.activity

import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import tp.info507.flavus.R
import tp.info507.flavus.model.Recipe
import tp.info507.flavus.storage.FavorisStorage

class RecipeActivity : AppCompatActivity() {
    private var recipe: Recipe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val id = intent.getIntExtra(MainActivity.EXTRA_MEAL, 0)
        Log.d("RecipeActivity", "Received recipe ID: $id")

        recipe = RecipeStorage.get(applicationContext).find(id)
        if (recipe == null) {
            Log.e("RecipeActivity", "No recipe found with ID: $id")
            Toast.makeText(this, "Recette introuvable", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setContentView(R.layout.activity_recipe)

        findViewById<TextView>(R.id.value).text = recipe?.nom
        findViewById<TextView>(R.id.description).text = recipe?.recette
        Glide.with(this)
            .load(recipe?.image)
            .into(findViewById<ImageView>(R.id.recipe_image))

        val favoriteCheckbox = findViewById<CheckBox>(R.id.favorite_checkbox)

        recipe?.let {
            val isFavorite = FavorisStorage.getFavorites(applicationContext).any { favRecipe ->
                favRecipe.id == it.id
            }
            Log.d("RecipeActivity", "Is recipe favorite: $isFavorite")
            favoriteCheckbox.isChecked = isFavorite
        }

        favoriteCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (recipe != null) {
                if (isChecked) {
                    FavorisStorage.addToFavorites(applicationContext, recipe!!)
                    Toast.makeText(this, "${recipe!!.nom} ajouté aux favoris", Toast.LENGTH_SHORT).show()
                } else {
                    FavorisStorage.removeFromFavorites(applicationContext, recipe!!)
                    Toast.makeText(this, "${recipe!!.nom} supprimé des favoris", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
