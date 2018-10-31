package com.skosc.tkffintech.ui.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.skosc.tkffintech.TKFApplication
import org.kodein.di.direct
import org.kodein.di.generic.instance
import kotlin.reflect.KClass

abstract class TKFFragment : Fragment() {
    fun <T : ViewModel> getViewModel(cls: KClass<T>): T {
        val tkfApplication = activity?.application as TKFApplication
        val factory = tkfApplication.kodein.direct.instance<ViewModelProvider.Factory>(cls)
        return ViewModelProviders.of(this, factory).get(cls.java)
    }
}