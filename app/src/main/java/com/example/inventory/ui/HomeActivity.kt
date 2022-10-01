package com.example.inventory.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.inventory.*
import com.example.inventory.databinding.ActivityHomeBinding
import com.example.inventory.model.HomeViewModel
import com.example.inventory.room.Material
import com.example.inventory.spread.showToast
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeActivity : BaseActivity() {
    private lateinit var mBinding:ActivityHomeBinding
    private val mModel:HomeViewModel by viewModels()
    private lateinit var handler: Handler
    private lateinit var sp:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        val naviHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val naviHostController = naviHostFragment.findNavController()
        mBinding.bottomNavi.setupWithNavController(naviHostController)
        sp = getSharedPreferences("TOKEN",Context.MODE_PRIVATE)
        handler = Handler(mainLooper)

        AppManager.apply {
            finishOneActivity(MainActivity::class.java.name)
            finishOneActivity(LoginActivity::class.java.name)
        }

        val username = sp.getString("TOKEN","= - =")
        mModel.name.value = username
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode!= RESULT_OK ||data ==null){
            return
        }
        when(requestCode){
            200 -> {
                val hmsScan: HmsScan? = data.getParcelableExtra(ScanUtil.RESULT) // 获取扫码结果 ScanUtil.RESULT
                if (!TextUtils.isEmpty(hmsScan?.getOriginalValue())) {
                    val char = hmsScan?.getOriginalValue()?.split(" ")
                    char?.let {
                        if(char[char.size-1]=="weifangzhou"&&char.size==8){
                            val temp = Material(char[0],char[1],"1",char[2],char[3],char[4],char[5],char[6])
                            lifecycleScope.launch(Dispatchers.IO){
                                if(mModel.insertOneMaterial(temp)){
                                    handler.post { "入库成功".showToast() }
                                }else{
                                    handler.post { "已存在，无需重复入库".showToast() }
                                }
                            }
                        }else{
                            Toast.makeText(MyApplication.context,"无效的🐎",Toast.LENGTH_SHORT).show()
                        }
                    }
                    }else{
                        Toast.makeText(MyApplication.context,"无效的🐎",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
}
