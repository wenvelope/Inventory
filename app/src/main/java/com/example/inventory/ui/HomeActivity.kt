package com.example.inventory.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.inventory.AppManager
import com.example.inventory.MainActivity
import com.example.inventory.R
import com.example.inventory.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var mBinding:ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_home)

        AppManager.apply {
            finishActivity(MainActivity::class.java)
            finishActivity(LoginActivity::class.java)
        }



    }
}