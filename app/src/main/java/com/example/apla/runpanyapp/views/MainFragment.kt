package com.example.apla.runpanyapp.views

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.apla.runpanyapp.R
import com.example.apla.runpanyapp.databinding.MainFragmentBinding
import com.example.apla.runpanyapp.viewmodels.MainViewModel

class MainFragment : BaseFragment() {

    private var mBinding: MainFragmentBinding? = null
    private var mViewModel: MainViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // ViewModel
        //DI
        val factory = MainViewModel.Factory(
                activity!!.application
        )
        //ViewModelインスタンスを取得
        mViewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
//        mViewModel = MainViewModel(application)

        // Binding
        mBinding = MainFragmentBinding.bind(view!!)
        mBinding!!.viewModel = mViewModel

    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel!!.destroy()
    }


    companion object {

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

}
