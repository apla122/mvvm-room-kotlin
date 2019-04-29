package com.example.apla.runpanyapp.views

import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import com.example.apla.runpanyapp.R
import com.example.apla.runpanyapp.databinding.MainActivityBinding
import com.example.apla.runpanyapp.viewmodels.MainViewModel

class MainActivity : BaseActivity(){

    private var mBinding: MainActivityBinding? = null
    private var mViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)

        // ViewModel
        //DI
        val factory = MainViewModel.Factory(
                application
        )
        //ViewModelインスタンスを取得
        mViewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
//        mViewModel = MainViewModel(application)

        // binding
        mBinding!!.viewModel = mViewModel

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

}
