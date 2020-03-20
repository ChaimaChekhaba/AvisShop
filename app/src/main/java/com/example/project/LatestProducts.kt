package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Type

class LatestProducts : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.latest_products)



        val p1 = Product ()
        p1.product_name = "Les Enfoirés 2020"
        p1.product_image = "https://pores-dilates.com/wp-content/uploads/2020/02/rouleau-jade-utilisation.png"

        val p2 = Product ()
        p2.product_name = "Pilates pour mincir en Forme"
        p2.product_image = "https://pores-dilates.com/wp-content/uploads/2020/02/rouleau-jade-utilisation.png"

        val p3 = Product ()
         p3.product_name = "Pilates Magic Circle-No Stress Fitness"
         p3.product_image = "https://pores-dilates.com/wp-content/uploads/2020/02/rouleau-jade-utilisation.png"

        val latestProds = ArrayList <Product>()

        latestProds.add(p1)
        latestProds.add(p2)
       latestProds.add(p3)

        viewManager = LinearLayoutManager(this)
        viewAdapter = AdapterLatestProducts(latestProds)


        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {

            setHasFixedSize(true)
            //définir layoutmanager et adapter de recycelrview
            layoutManager = viewManager
            adapter = viewAdapter

        }

    }


}
