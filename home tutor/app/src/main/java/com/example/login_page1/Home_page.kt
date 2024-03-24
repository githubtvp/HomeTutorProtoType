package com.example.login_page1

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.login_page1.databinding.ActivityHomePageBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.navigation.NavigationView


class Home_page : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityHomePageBinding
  //  private lateinit var nextPage: Class<*>
  //  private var nextPageCreateProfile: Class<*> = CreateProfile::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomAppBar: BottomAppBar = findViewById(R.id.bottom_app_bar)
        setSupportActionBar(bottomAppBar)

        setSupportActionBar(binding.toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.nav_open,
            R.string.nav_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationDrawer.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
            binding.navigationDrawer.setCheckedItem(androidx.appcompat.R.id.home) // Set default checked item in navigation drawer
        }

        initUI()
    }

    private fun initUI() {
        var nextPg : Intent  //(this, nextPage)
        binding.createProfile.setOnClickListener {
           // val intent = Intent(this, page1::class.java)
            nextPg = Intent(this, CreateProfile::class.java)
            startActivity(nextPg)
        }
        binding.editProfile.setOnClickListener {
          //  nextPg = Intent(this, nextPage)
        }
        binding.stuDashboard.setOnClickListener {
          //  nextPg = Intent(this, nextPage)
        }
        binding.teachDashboard.setOnClickListener {
          //  nextPg = Intent(this, nextPage)
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bottom_home -> replaceFragment(HomeFragment())
            R.id.bottom_chat -> replaceFragment(SearchFragment())
            R.id.bottom_search -> replaceFragment(ChatFragment())
            R.id.nav_acc -> replaceFragment(AccountFragment())
            R.id.nav_Setting -> replaceFragment(SettingsFragment())
            R.id.nav_bookmark -> replaceFragment(SavedFragment())
            R.id.nav_about -> replaceFragment(AboutFragment())
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    // Define your fragment classes here

    class HomeFragment : Fragment() {
        // Your fragment implementation
    }

    class SearchFragment : Fragment() {
        // Your fragment implementation
    }

    class ChatFragment : Fragment() {
        // Your fragment implementation
    }

    class AccountFragment : Fragment() {
        // Your fragment implementation
    }

    class SettingsFragment : Fragment() {
        // Your fragment implementation
    }

    class SavedFragment : Fragment() {
        // Your fragment implementation
    }

    class AboutFragment : Fragment() {
        // Your fragment implementation
    }

}

