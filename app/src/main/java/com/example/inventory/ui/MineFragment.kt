package com.example.inventory.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.inventory.R
import com.example.inventory.databinding.FragmentMineBinding
import com.example.inventory.model.HomeViewModel
import com.example.inventory.spread.startActivity


class MineFragment : Fragment() {
    private lateinit var mBinding:FragmentMineBinding
    private val mModel by activityViewModels<HomeViewModel>()
    private lateinit var sp:SharedPreferences
    override fun onAttach(context: Context) {
        super.onAttach(context)
        sp = context.getSharedPreferences("TOKEN",Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }

}