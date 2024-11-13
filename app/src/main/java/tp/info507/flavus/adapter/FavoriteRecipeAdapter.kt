package tp.info507.flavus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tp.info507.flavus.R
import tp.info507.flavus.model.Recipe

class FavoriteRecipeAdapter(
    private val context: Context,
    private val recipes: List<Recipe>,
    private val onItemClick: (Recipe) -> Unit
) : RecyclerView.Adapter<FavoriteRecipeAdapter.FavoriteRecipeHolder>() {

    class FavoriteRecipeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: LinearLayout = itemView.findViewById(R.id.recipe_layout)
        val value: TextView = itemView.findViewById(R.id.recipe_value)
        val image: ImageView = itemView.findViewById(R.id.recipe_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRecipeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return FavoriteRecipeHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteRecipeHolder, position: Int) {
        val recipe = recipes[position]
        holder.value.text = recipe.nom

        Glide.with(context)
            .load(recipe.image)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            onItemClick(recipe)
        }
    }

    override fun getItemCount(): Int = recipes.size
}
