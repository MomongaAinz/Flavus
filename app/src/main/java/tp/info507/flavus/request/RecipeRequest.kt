package tp.info507.flavus.request

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import tp.info507.flavus.R
import tp.info507.flavus.activity.Updatable
import tp.info507.flavus.model.Recipe

class RecipeRequest(private val context: Context, private val updatable: Updatable, private val recheche: String) {
    private var isRequesting = false

    init {
        if (!isRequesting) {
            isRequesting = true

            val request = JsonObjectRequest(
                Request.Method.GET,
                "https://www.themealdb.com/api/json/v1/1/search.php?s=" + recheche,
                null,
                { res ->
                    Log.d("RecipeRequest", res.toString())
                    if (!res.isNull("meals")) {
                        refresh(res)
                        Toast.makeText(context, R.string.success, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, R.string.failure, Toast.LENGTH_SHORT).show()
                    }
                    isRequesting = false
                    updatable.update()  // Appel pour arrêter l'animation
                },
                { err ->
                    Toast.makeText(context, "Erreur: ${err.message}", Toast.LENGTH_SHORT).show()
                    isRequesting = false
                    updatable.update()  // Appel pour arrêter l'animation même en cas d'erreur
                }
            )

            val queue = Volley.newRequestQueue(context)
            queue.add(request)
            queue.start()
        }
    }

    private fun refresh(res: JSONObject) {
        delete()
        insert(res)
    }

    private fun delete() {
        for (recipe in RecipeStorage.get(context).findAll()) {
            RecipeStorage.get(context).delete(recipe.id)
        }
    }

    private fun insert(json: JSONObject) {
        Log.d("RecipeRequest", "Inserting recipes...")

        val recipes = json.getJSONArray("meals")
        Log.d("RecipeRequest", "Number of recipes: ${recipes.length()}")

        for (i in 0 until recipes.length()) {
            val recipe = recipes.getJSONObject(i)
            val recipeName = recipe.getString(Recipe.NOM)
            val recipeInstructions = recipe.getString(Recipe.RECETTE)
            val recipeImage = recipe.getString(Recipe.IMAGE)
            Log.d("RecipeRequest", "Inserting recipe: $recipeName")

            RecipeStorage.get(context).insert(
                Recipe(
                    0,
                    recipeName,
                    recipeInstructions,
                    recipeImage
                )
            )
        }

        Log.d("RecipeRequest", "Finished inserting recipes.")
    }
}
