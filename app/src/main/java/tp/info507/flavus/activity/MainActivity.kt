package tp.info507.flavus.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.google.android.material.bottomnavigation.BottomNavigationView
import tp.info507.flavus.R
import tp.info507.flavus.adapter.RecipeAdapter
import tp.info507.flavus.request.RecipeRequest

class MainActivity : AppCompatActivity(), Updatable {
    companion object {
        const val EXTRA_MEAL = "EXTRA_MEAL"
    }

    private lateinit var refresh: SwipeRefreshLayout
    private lateinit var list: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list = findViewById(R.id.recipe_list)
        refresh = findViewById(R.id.refresh_layout)


        RecipeRequest(applicationContext,this,"chicken")

        val spanCount = 3 // Nombre de colonnes

        // Configure le GridLayoutManager avec le nombre de colonnes
        val layoutManager = GridLayoutManager(this, spanCount)
        list.layoutManager = layoutManager


        // Adapter pour la RecyclerView
        list.adapter = object : RecipeAdapter(context = applicationContext) {
            override fun onItemClick(view: View) {
                var intent = Intent(applicationContext, RecipeActivity::class.java).apply{
                    putExtra(EXTRA_MEAL, view.tag as Int)
                }
                startActivity(intent)
            }
        }



        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_main -> {
                    // Reste dans MainActivity
                    true
                }
                R.id.navigation_favorites -> {
                    startActivity(Intent(this, FavoriteActivity::class.java))
                    true
                }
                else -> false
            }
        }


        refresh.setOnRefreshListener {
            RecipeRequest(applicationContext, this,"pork")
        }

        findViewById<SwipeRefreshLayout>(R.id.refresh_layout).setOnRefreshListener {
            RecipeRequest(applicationContext, this,"pork")
        }

        findViewById<ImageView>(R.id.recherche_image).setOnClickListener() {
            val recherche = findViewById<EditText>(R.id.search_bar).text.toString()
            RecipeRequest(applicationContext, this, recherche)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile -> {
                startActivity(Intent(applicationContext, ProfileActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun update() {// RAJOUTER OVERRID SI ONLONGLISTENER
        list.adapter?.notifyDataSetChanged()
        refresh.isRefreshing = false
    }
}
