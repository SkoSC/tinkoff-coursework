package com.skosc.tkffintech.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T : Any> LiveData<T>.observe(owner: LifecycleOwner, callback: (T) -> Unit) {
    this.observe(owner, Observer(callback))
}

@JvmName("liveDataObserveNullable")
fun <T> LiveData<T>.observe(owner: LifecycleOwner, callback: (T) -> Unit) {
    this.observe(owner, Observer(callback))
}