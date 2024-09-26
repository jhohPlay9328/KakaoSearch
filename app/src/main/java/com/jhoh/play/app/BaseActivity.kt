package com.jhoh.play.app

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseActivity<DB: ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes val layoutResId: Int
): AppCompatActivity() {
    abstract val viewModel: VM

    private lateinit var _binding: DB
    protected val binding get() = _binding

    protected var savedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, 0, 0)
        }else {
            @Suppress("DEPRECATION")
            overridePendingTransition(0, 0)
        }

        if(layoutResId > 0) {
            _binding = DataBindingUtil.setContentView<DB>(this, layoutResId).apply {
                lifecycleOwner = this@BaseActivity
                setVariable(BR.viewModel, viewModel)
            }
        }

        loadView()
        registerListener()
        registerFlow()
    }

    override fun onPause() {
        super.onPause()

        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_CLOSE, 0, 0)
        }else {
            @Suppress("DEPRECATION")
            overridePendingTransition(0, 0)
        }
    }

    protected open fun loadView() {}

    protected open fun registerListener() {}

    protected open fun registerFlow(){
        lifecycleScope.launch {
            viewModel.showApiErrorPopup.collect {
                showApiErrorPopup(it)
            }
        }
    }

    protected open fun showApiErrorPopup(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    protected fun finishApp() {
        finishAffinity()
    }

    fun LifecycleCoroutineScope.repeatLaunchWhenCreated(
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                block()
            }
        }
    }

    val Int.dp: Float
        get() {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                this.toFloat(),
                resources.displayMetrics
            )
        }
}