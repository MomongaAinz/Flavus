package tp.info507.flavus.adapter;

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import tp.info507.flavus.R


abstract class RecipeAdapter(
    private val context: Context): RecyclerView.Adapter<RecipeAdapter.RecipeHolder>() {



    class RecipeHolder(itemView: View): ViewHolder(itemView){
        val layout: LinearLayout = itemView.findViewById(R.id.recipe_layout)
        val value: TextView = itemView.findViewById(R.id.recipe_value)
        val image: ImageView = itemView.findViewById(R.id.recipe_image)
    }

    abstract fun onItemClick(view: View)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        view.setOnClickListener() {
            onItemClick(view)
        }
        return RecipeHolder(view)
    }
    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        val recipe = RecipeStorage.get(context).findAll()[position]
        holder.itemView.tag = recipe.id
        holder.value.text = recipe.nom

        val imageUrl = recipe.image
        Glide.with(context)
            .load(imageUrl)
            .into(holder.image)
    }


    override fun getItemCount(): Int {
        return RecipeStorage.get(context).size()
    }
}








