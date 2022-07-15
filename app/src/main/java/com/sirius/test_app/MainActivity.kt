package com.sirius.test_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sirius.test_app.ui.game_detail.GameDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, GameDetailsFragment())
                .commit()
        }
    }
}