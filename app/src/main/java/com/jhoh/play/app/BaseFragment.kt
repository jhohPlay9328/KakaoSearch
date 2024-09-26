package com.jhoh.play.app

import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


abstract class BaseFragment<DB: ViewDataBinding, VM: BaseViewModel>(
    @LayoutRes open val layoutResId: Int,
): Fragment() {
    abstract val viewModel: VM
    abstract val activityViewModel: BaseViewModel

    private lateinit var _binding: DB
    protected val binding get() = _binding

    protected var savedInstanceState: Bundle? = null


    // argument를 전달 받는 부분
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.savedInstanceState = savedInstanceState
    }

    // View를 초기화하는 부분
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        with(_binding){
            lifecycleOwner = this@BaseFragment
            setVariable(BR.viewModel, viewModel)
        }

        loadView()
        registerListener()
        registerFlow()
        return binding.root
    }

    protected open fun loadView() {}

    protected open fun registerListener() {}


    protected open fun registerFlow(){
        lifecycleScope.launch {
            viewModel.showApiErrorPopup.collect {
                activityViewModel.showApiErrorPopup(it)
            }
        }
    }

    protected val Int.dpToPxF: Float
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    this.toFloat(),
                    resources.displayMetrics
                )
            } else {
                this * resources.displayMetrics.density
            }
        }
}