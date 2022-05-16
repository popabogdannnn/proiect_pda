package com.example.proiect_pda

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.proiect_pda.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_camera.*


class MainActivity : AppCompatActivity() {
    private val camera_fragment = camera()
    private val settings_fragment = settings()
    private val home_fragment = home()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        change_fragment(home_fragment)

        bottom_navigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.ic_settings -> change_fragment(settings_fragment)
                R.id.ic_home -> change_fragment(home_fragment)
                R.id.ic_camera -> change_fragment(camera_fragment)
            }
            true
        }

    }

    private fun change_fragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }
}