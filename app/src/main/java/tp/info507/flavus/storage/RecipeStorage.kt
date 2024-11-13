import android.content.Context
import android.content.SharedPreferences
import tp.info507.flavus.model.Recipe
import tp.info507.flavus.storage.RecipeJSONFileStorage
import tp.info507.flavus.storage.Storage

object RecipeStorage {
    private const val LOGIN = "login"
    private const val STORAGE = "tp/info507/flavus/storage"

    fun get(context: Context): Storage<Recipe> {
        return RecipeJSONFileStorage(context)
    }

}