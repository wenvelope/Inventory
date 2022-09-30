package com.example.inventory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.removeActivity(this)
    }
}