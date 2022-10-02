package com.example.inventory.ui

import android.content.Context
import android.content.SharedPreferences
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                }else{
                    mHandler.post { "网络错误".showToast() }
                }
            }
        }

    }

}