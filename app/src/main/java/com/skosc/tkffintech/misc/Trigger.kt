package com.skosc.tkffintech.misc

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * Represents lifecycle aware relation: event <-> observer
 */
class Trigger {
    private val livedata = MutableLiveData<Long>().apply {
        value = 0
    }

    /**
     * Register observer
     */
    fun observe(owner: LifecycleOwner, action: () -> Unit) {
        livedata.observe(owner, Observer { action.invoke() })
    }

    /**
     * Fire event to observers
     */
    fun fire() {
        livedata.value = livedata.value!!.inc()
    }
}