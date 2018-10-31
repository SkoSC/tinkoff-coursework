package com.skosc.tkffintech.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class RxViewModel : ViewModel() {
    protected val cdisp: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        cdisp.dispose()
    }
}