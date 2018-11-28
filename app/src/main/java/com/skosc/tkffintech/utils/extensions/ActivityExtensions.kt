package com.skosc.tkffintech.utils.extensions

import android.content.Intent
import com.skosc.tkffintech.ui.activity.TKFActivity
import kotlin.reflect.KClass

/**
 * Navigates to [TKFActivity]
 * @return Closure navigating to passed activity
 */
fun <T: TKFActivity> TKFActivity.navigateTo(destination: KClass<T>): () -> Unit = {
    val intent = Intent(this, destination.java)
    startActivity(intent)
}