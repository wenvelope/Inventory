package com.example.inventory

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import com.example.inventory.ui.HomeActivity
import com.example.inventory.spread.startActivity
import com.example.inventory.ui.LoginActivity

class MainActivity :BaseActivity() {
    private lateinit var sp:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sp = getSharedPreferences("TOKEN",Context.MODE_PRIVATE)
        val handler = Handler(mainLooper)
        val intent = Intent(this,LoginActivity::class.java)
        handler.postDelayed({
            if(sp.getString("TOKEN","SB")!="SB"){
                this.startActivity<HomeActivity> {  }
            }else{
                startActivity(intent)
            }
            },2000)
    }
}