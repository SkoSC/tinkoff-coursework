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
        return tkfActivity.getViewModel(cls)
    }

    fun <T : ViewModel> getViewModel(cls: KClass<T>, args: ViewModelArgs): T {
        return tkfActivity.getViewModel(cls, args)
    }

    private val tkfActivity: TKFActivity
        get() {
            if (activity !is TKFActivity) throw IllegalStateException(ACTIVITY_NOT_ATTACHED)
            return activity as TKFActivity
        }
}