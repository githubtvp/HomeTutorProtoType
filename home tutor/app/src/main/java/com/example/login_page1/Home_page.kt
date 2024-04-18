package com.example.login_page1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.login_page1.databinding.ActivityHomePageBinding

@Suppress("DEPRECATION")
class Home_page : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding

    private val nextPageCreateProfile: Class<*> = CreateProfile::class.java
    private val nextPageCategory: Class<*> = Category::class.java
   // private val nextPageTest: Class<*> = FbCrud::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        binding.navigationDrawer.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_acc -> replaceFragment(accountfragment())
                R.id.nav_bookmark -> replaceFragment(savedfragment())
                R.id.nav_Setting -> replaceFragment(settingsfragement())
                R.id.nav_about -> replaceFragment(aboutfragment())
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bot_home -> replaceFragment(Homefragement())
                R.id.bot_search -> replaceFragment(Searchfragement())
                R.id.bot_chat -> replaceFragment(chatfragment())
            }
            true
        }

        if (savedInstanceState == null) {
            replaceFragment(Homefragement())
            binding.navigationDrawer.setCheckedItem(androidx.appcompat.R.id.home)
        }

        // initUI()
    }

//    private fun initUI() {
//        binding.searchActivity.setOnClickListener {
//            startNextPage(nextPageCategory)
//        }
//
//        binding.search.setOnClickListener {
//            startNextPage(nextPageCategory)
//        }
//
//        binding.profile.setOnClickListener {
//            startNextPage(nextPageCreateProfile)
//        }
//
//        binding.editProfile.setOnClickListener {
//            startNextPage(nextPageCreateProfile)
//        }
//    }

    private fun startNextPage(nextPage: Class<*>) {
        val intent = Intent(this, nextPage)
        startActivity(intent)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    // Define your fragment classes here

//    class HomeFragment : Fragment() {
//        // Your fragment implementation
//    }
//
//    class SearchFragment : Fragment() {
//        // Your fragment implementation
//    }
//
//    class ChatFragment : Fragment() {
//        // Your fragment implementation
//    }
//
//    class AccountFragment : Fragment() {
//        // Your fragment implementation
//    }
//
//    class SettingsFragment : Fragment() {
//        // Your fragment implementation
//    }
//
//    class SavedFragment : Fragment() {
//        // Your fragment implementation
//    }
//
//    class AboutFragment : Fragment() {
//        // Your fragment implementation
//    }
}