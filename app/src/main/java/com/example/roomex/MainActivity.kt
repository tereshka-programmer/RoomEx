package com.example.roomex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Repository.init(applicationContext)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}