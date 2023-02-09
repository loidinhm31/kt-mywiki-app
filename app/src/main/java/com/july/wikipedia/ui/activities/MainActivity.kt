package com.july.wikipedia.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.july.wikipedia.R
import com.july.wikipedia.ui.fragments.ExploreFragment
import com.july.wikipedia.ui.fragments.FavoritesFragment
import com.july.wikipedia.ui.fragments.HistoryFragment

class MainActivity : AppCompatActivity() {
    private val exploreFragment: ExploreFragment = ExploreFragment()
    private val favoriteFragment: FavoritesFragment = FavoritesFragment()
    private val historyFragment: HistoryFragment = HistoryFragment()

    private val mOnNavigationItemSelectedListener =
        NavigationBarView.OnItemSelectedListener { item ->
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)

            when (item.itemId) {
                R.id.navigation_explore -> transaction.replace(
                    R.id.fragment_container,
                    exploreFragment
                )
                R.id.navigation_favorites -> transaction.replace(
                    R.id.fragment_container,
                    favoriteFragment
                )
                R.id.navigation_history -> transaction.replace(
                    R.id.fragment_container,
                    historyFragment
                )
            }
            transaction.commit()

            true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        val navBar = findViewById<BottomNavigationView>(R.id.navigation)
        navBar.setOnItemSelectedListener(mOnNavigationItemSelectedListener)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, exploreFragment)
        transaction.commit()

    }
}
