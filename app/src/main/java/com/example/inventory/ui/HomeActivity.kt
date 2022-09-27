package com.example.inventory.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.inventory.AppManager
import com.example.inventory.MainActivity
import com.example.inventory.R
import com.example.inventory.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var mBinding:ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_home)
        val naviHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val naviHostController = naviHostFragment.findNavController()
        mBinding.bottomNavi.setupWithNavController(naviHostController)


        AppManager.apply {
            finishActivity(MainActivity::class.java)
            finishActivity(LoginActivity::class.java)
        }



    }
}