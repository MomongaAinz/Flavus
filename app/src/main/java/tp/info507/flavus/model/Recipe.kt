package tp.info507.flavus.model

class Recipe(val id: Int, val nom: String, val recette: String, val image: String) {
    companion object {
        const val ID = "id"
        const val NOM = "strMeal"
        const val RECETTE = "strInstructions"
        const val IMAGE = "strMealThumb"
    }
}