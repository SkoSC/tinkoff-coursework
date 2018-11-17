package com.skosc.tkffintech.ui.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.skosc.tkffintech.ui.activity.TKFActivity
import com.skosc.tkffintech.viewmodel.ViewModelArgs
import kotlin.reflect.KClass

abstract class TKFFragment : Fragment() {
    companion object {
        private const val ACTIVITY_NOT_ATTACHED = "TKF fragment was not attached to TKFActivity"
    }

    fun <T : ViewModel> getViewModel(cls: KClass<T>): T {
        if (activity !is TKFActivity) throw IllegalStateException(ACTIVITY_NOT_ATTACHED)
        val tkfActivity = activity as TKFActivity
        return tkfActivity.getViewModel(cls)
    }

    fun <T : ViewModel> getViewModel(cls: KClass<T>, args: ViewModelArgs): T {
        if (activity !is TKFActivity) throw IllegalStateException(ACTIVITY_NOT_ATTACHED)
        val tkfActivity = activity as TKFActivity
        return tkfActivity.getViewModel(cls, args)
    }
}