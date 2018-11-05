package com.skosc.tkffintech.ui.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.skosc.tkffintech.TKFApplication
import com.skosc.tkffintech.ui.activity.TKFActivity
import org.kodein.di.direct
import org.kodein.di.generic.instance
import java.lang.IllegalStateException
import kotlin.reflect.KClass

abstract class TKFFragment : Fragment() {
    fun <T : ViewModel> getViewModel(cls: KClass<T>): T {
        if (activity !is TKFActivity) throw IllegalStateException("TKF fragment was not attached to TKFActivity")
        val tkfActivity = activity as TKFActivity
        return tkfActivity.getViewModel(cls)
    }
}