package com.skosc.tkffintech.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class Trigger {
    private val livedata = MutableLiveData<Long>().apply {
        value = 0
    }

    fun observe(owner: LifecycleOwner, action: () -> Unit) {
        livedata.observe(owner, Observer { action.invoke() })
    }

    fun trigger() {
        livedata.value = livedata.value!!.inc()
    }
}