package com.example.inventory.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.inventory.*
import com.example.inventory.databinding.ActivityHomeBinding
import com.example.inventory.model.HomeViewModel
import com.example.inventory.spread.showToast
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan

class HomeActivity : BaseActivity() {
    private lateinit var mBinding:ActivityHomeBinding
    private val mModel:HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_home)
        val naviHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val naviHostController = naviHostFragment.findNavController()
        mBinding.bottomNavi.setupWithNavController(naviHostController)
        AppManager.apply {
            finishOneActivity(MainActivity::class.java.name)
            finishOneActivity(LoginActivity::class.java.name)
        }
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
                        hmsScan?.getOriginalValue()?.showToast()
                    }else{
                        Toast.makeText(MyApplication.context,"无效的🐎",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
}
