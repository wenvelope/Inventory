package com.example.inventory.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.inventory.R
import com.example.inventory.databinding.FragmentMineBinding
import com.example.inventory.model.HomeViewModel
import com.example.inventory.repository.Repository
import com.example.inventory.spread.showToast
import com.example.inventory.spread.startActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MineFragment : Fragment() {
    private lateinit var mBinding:FragmentMineBinding
    private val mModel by activityViewModels<HomeViewModel>()
    private lateinit var sp:SharedPreferences
    private lateinit var mHandler:Handler
    private lateinit var up:String
    private lateinit var down:String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sp = context.getSharedPreferences("TOKEN",Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHandler = Handler(Looper.getMainLooper())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_mine,container,false)
        return mBinding.root
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val area = sp.getString("AREA","1")
        when(area){
            "1"->{
                up = sp.getString("仓库A_UP","-1").toString()
                down = sp.getString("仓库A_DOWN","-1").toString()
            }
            "2"->{
                up = sp.getString("仓库B_UP","-1").toString()
                down = sp.getString("仓库B_DOWN","-1").toString()
            }
            "3"->{
                up = sp.getString("仓库C_UP","-1").toString()
                down = sp.getString("仓库C_DOWN","-1").toString()
            }
            "4"->{
                up = sp.getString("仓库D_UP","-1").toString()
                down = sp.getString("仓库D_DOWN","-1").toString()
            }
        }

        if (up!="-1"&&down!="-1"){
            mBinding.alarm.text = "上限预警："+up+"下限预警："+down
        }



        activity?.apply {
            mModel.name.observe(this){
                mBinding.username.text = it
            }
            mBinding.logOut.setOnClickListener {
                this.startActivity<LoginActivity> {
                    sp.edit().apply{
                        putString("TOKEN","SB")
                        apply()
                    }
                }
            }
        }

        lifecycleScope.launch (Dispatchers.IO){
            val s = sp.getString("AREA","0")
            s?.let {
                val result = Repository.selectAllByRe(it).getOrNull()
                if(result!=null){
                    mBinding.areaNum.text = result.materialList.size.toString()
                    if (up!="-1"&&down!="-1"){
                        if(result.materialList.size>up.toInt()||result.materialList.size<down.toInt()){
                            mBinding.formConsume.setBackgroundColor(Color.RED)
                        }
                    }
                }else{
                    mHandler.post { "网络错误".showToast() }
                }
            }
        }

    }

}