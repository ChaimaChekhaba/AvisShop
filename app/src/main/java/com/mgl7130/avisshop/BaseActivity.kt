package com.mgl7130.avisshop

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.app_bar_drawer.*


//this activity handle the navigation menu used in all the activities
open class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    open val TAG = "BaseActivity"

    lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setSupportActionBar(toolbar)
        mDrawerLayout = drawer_layout
        init()
    }
    // handling the menu
    fun init(){
        val toogle = ActionBarDrawerToggle(Activity(), drawer_layout, toolbar, R.string.nav_open, R.string.nav_close)
        drawer_layout.addDrawerListener(toogle)
        toogle.syncState()
        navigation_view.setNavigationItemSelectedListener(this)

    }
    //managing the navigation in the menu
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId){
            R.id.new_evaluation ->{
                if (this.TAG != "MainActivity"){
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    startActivity(intent)

                    if (this.TAG == "ProductDetailsActivity")
                        finish()
                }
            }
            R.id.history ->{
                val intent = Intent(this, LatestProductActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                startActivity(intent)

                if (this.TAG == "ProductDetailsActivity")
                    finish()
            }
            R.id.about ->{
                Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Log.i("error", "item id does not exist")
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
