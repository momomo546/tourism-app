package com.example.kenroku_app.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kenroku_app.R
import com.example.kenroku_app.view.fragments.selectMap.SelectMapFragment

class SelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        // Fragmentを表示
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SelectMapFragment())
            .commit()
    }
}