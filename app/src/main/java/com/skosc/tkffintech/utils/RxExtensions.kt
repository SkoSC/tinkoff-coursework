package com.skosc.tkffintech.utils

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

infix fun CompositeDisposable.own(disp: Disposable) {
    this.add(disp)
}

fun <T, F> Single<List<T>>.mapEach(fn: (T) -> F): Single<List<F>> = this.map { it.map(fn) }
fun <T, F> Observable<List<T>>.mapEach(fn: (T) -> F): Observable<List<F>> = this.map { it.map(fn) }

fun <T> Single<List<T>>.filterEach(fn: (T) -> Boolean): Single<List<T>> = this.map { it.filter(fn) }

fun <T> Single<T>.observeOnMainThread() = this.observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.subscribeOnIoThread() = this.subscribeOn(Schedulers.io())