package com.jhoh.play.app.common.extension

import android.content.Context
import android.os.Build
import android.util.TypedValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun LifecycleCoroutineScope.repeatLaunchWhenCreated(
    lifecycleOwner: LifecycleOwner,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
            block()
        }
    }
}

fun LifecycleCoroutineScope.repeatLaunchWhenStarted(
    lifecycleOwner: LifecycleOwner,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            block()
        }
    }
}

fun Int.dpToPx(context: Context) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    )
} else {
    this * context.resources.displayMetrics.density
}.toInt()