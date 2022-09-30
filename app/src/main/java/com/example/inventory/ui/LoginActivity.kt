package com.example.inventory.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.inventory.AppManager
import com.example.inventory.BaseActivity
import com.example.inventory.MainActivity
import com.example.inventory.R
import com.example.inventory.databinding.ActivityLoginBinding
import com.example.inventory.network.InventoryNetWork
import com.example.inventory.spread.showToast
import com.example.inventory.spread.startActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginActivity : BaseActivity() {
    private lateinit var mBinding:ActivityLoginBinding
    private lateinit var sp:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.finishOneActivity(MainActivity::class.java.name)

        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        sp = getSharedPreferences("TOKEN", Context.MODE_PRIVATE)


        mBinding.apply {
            button.setOnClickListener {
                val username =usernameEdit.text.toString()
                val password = passwordEdit.text.toString()
                if(username.isEmpty()||password.isEmpty()){
                    "用户名或者密码为空".showToast()
                }else{
                    lifecycleScope.launch {
                        val response = withContext(Dispatchers.IO){
                            val result = try {
                                val responseBody =InventoryNetWork.getUserId(username,password).string()
                                Result.success(responseBody)
                            }catch (e:Exception){
                                Result.failure(e)
                            }
                            result.getOrNull()
                        }
                        if(response!=null){
                            when(response){
                                "登陆成功"->{
                                    this@LoginActivity.startActivity<HomeActivity> {
                                        sp.edit().apply {
                                            putString("TOKEN",username)
                                            apply()
                                        }
                                    }
                                }
                                else->{
                                    response.showToast()
                                }
                            }
                        }else{
                            "网络错误".showToast()
                        }

                    }
                }

            }
        }

    }
}