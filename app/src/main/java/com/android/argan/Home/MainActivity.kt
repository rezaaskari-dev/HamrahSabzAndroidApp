package com.android.argan.Home

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.argan.AboutFragment
import com.android.argan.CalculatorFragment
import com.android.argan.ConsumptionFragment
import com.android.argan.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var frameLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)



        bottomNavigationView = findViewById(R.id.bottom_navigation)
        frameLayout = findViewById(R.id.frame_main)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.calculator -> {
                    loadFragment(CalculatorFragment())
                    true
                }
                R.id.registration -> {
                    loadFragment(ConsumptionFragment())
                    true
                }
                R.id.about -> {
                    loadFragment(AboutFragment())
                    true
                }

                else -> false
            }
        }

        // بارگذاری اولیه HomeFragment
        loadFragment(HomeFragment())
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_main, fragment)
            .commit()
    }
}
