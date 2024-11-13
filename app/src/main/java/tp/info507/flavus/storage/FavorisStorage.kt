package tp.info507.flavus.storage

import android.content.Context
import tp.info507.flavus.model.Recipe

object FavorisStorage {

    fun getStorage(context: Context): FavorisJSONFileStorage {
        return FavorisJSONFileStorage(context)
    }

    fun addToFavorites(context: Context, recipe: Recipe) {
        val storage = getStorage(context)
        storage.insert(recipe)
    }

    fun removeFromFavorites(context: Context, recipe: Recipe) {
        val storage = getStorage(context)
        storage.delete(recipe.id)
    }

    fun getFavorites(context: Context): List<Recipe> {
        val storage = getStorage(context)
        return storage.findAll()
    }
}
