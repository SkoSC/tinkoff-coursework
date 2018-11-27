package com.skosc.tkffintech.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun <T: Any> LiveData<T>.observe(owner: LifecycleOwner, callback: (T) -> Unit) {

}