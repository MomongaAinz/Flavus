package tp.info507.flavus.storage

import android.content.Context
import org.json.JSONObject
import tp.info507.flavus.model.Recipe
import tp.info507.flavus.storage.utility.file.JSONFileStorage

class RecipeJSONFileStorage(context: Context): JSONFileStorage<Recipe>(context, "recipe") {

    override fun create(id: Int,obj: Recipe): Recipe {
        return Recipe(id, obj.nom, obj.recette,obj.image)
    }
    override fun objectToJson(id: Int, obj: Recipe): JSONObject {
        val json = JSONObject()
        json.put(Recipe.ID, obj.id)
        json.put(Recipe.NOM, obj.nom)
        json.put(Recipe.RECETTE, obj.recette)
        json.put(Recipe.IMAGE, obj.image)
        return json
    }
    override  fun jsonToObject(json: JSONObject): Recipe {
        return Recipe(
            json.getInt(Recipe.ID),
            json.getString(Recipe.NOM),
            json.getString(Recipe.RECETTE),
            json.getString(Recipe.IMAGE)
        )
    }
}