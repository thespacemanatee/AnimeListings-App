package com.example.animelistings

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.activity.compose.setContent
import androidx.navigation.fragment.NavHostFragment
import com.example.animelistings.databinding.ActivityMainBinding
import com.example.animelistings.ui.AnimeListingsApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
//    private val navHostFragment by lazy {
//        supportFragmentManager
//            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
//    }
//    private val navController by lazy { navHostFragment.navController }
//    private val appBarConfiguration by lazy { AppBarConfiguration(navController.graph) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimeListingsApp()
        }
//        binding.run {
//            setContentView(root)
//            setSupportActionBar(toolbar)
//            setupActionBarWithNavController(navController, appBarConfiguration)
//        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }
}