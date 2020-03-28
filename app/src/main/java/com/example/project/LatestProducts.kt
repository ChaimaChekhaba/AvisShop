package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.project.roomPersistence.DBproduit
import com.example.project.roomPersistence.InsertAsync
import com.example.project.roomPersistence.Produit
import kotlinx.android.synthetic.main.activity_latest_products.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*


class LatestProducts : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    lateinit var latestProds : List<Produit>
    var insertAsync = InsertAsync()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.latest_products)

        val p1 = Produit()

        p1.apply {
            NomProduit  = "Pilates pour mincir en Forme"
            ImageProduit = "https://pores-dilates.com/wp-content/uploads/2020/02/rouleau-jade-utilisation.png"
        }
        val p2 = Produit()

        p2.apply {
            NomProduit = "Les Enfoirés 2020"
            ImageProduit = "https://pores-dilates.com/wp-content/uploads/2020/02/rouleau-jade-utilisation.png"
        }
        val p3 = Produit()
        p3.apply {
            NomProduit = "Pilates Magic Circle-No Stress Fitness"
            ImageProduit = "https://pores-dilates.com/wp-content/uploads/2020/02/rouleau-jade-utilisation.png"
        }


        insertAsync.execute(p1)
        insertAsync.execute(p2)
        insertAsync.execute(p3)


         val thread = Thread ( Runnable {

           latestProds   = DBproduit.getInstance(this.applicationContext)
                .produitDAO().getLastThree()

        } )
        thread.start()

        viewManager = LinearLayoutManager(this)
        viewAdapter = AdapterLatestProducts(latestProds)
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {

            setHasFixedSize(true)
            //définir layoutmanager et adapter de recycelrview
            layoutManager = viewManager
            adapter = viewAdapter


            //   latestProds.forEach() {
            //      Log.i("produit", "Nom : ${it.NomProduit}")
            //    Log.i("produit", "Image : ${it.ImageProduit}")
            //}
            // }
            //val p1 = Product ()
            //p1.product_name = "Les Enfoirés 2020"
            //p1.product_image = "https://pores-dilates.com/wp-content/uploads/2020/02/rouleau-jade-utilisation.png"

            // val p2 = Product ()
            // p2.product_name = "Pilates pour mincir en Forme"
            // p2.product_image = "https://pores-dilates.com/wp-content/uploads/2020/02/rouleau-jade-utilisation.png"

            //val p3 = Product ()
            // p3.product_name = "Pilates Magic Circle-No Stress Fitness"
            // p3.product_image = "https://pores-dilates.com/wp-content/uploads/2020/02/rouleau-jade-utilisation.png"


        }

    }

}





