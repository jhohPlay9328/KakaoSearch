package com.jhoh.play.app.common.listener

import android.view.View

abstract class InteractionListener {
    open fun <T>onClick(view: View, item : T) {}
}