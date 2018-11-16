package com.skosc.tkffintech.utils

import android.util.Log
import com.skosc.tkffintech.BuildConfig
import org.kodein.di.Kodein

private const val KODEIN_MODULE_LOG_TAG = "KODEIN-MODULE"

/**
 * Crates [Kodein.Module] with appropriate default settings and prefix based on build settings
 */
fun Kodein.Companion.DefaultModule(name: String, builder: Kodein.Builder.() -> Unit) : Kodein.Module {
    Log.v(KODEIN_MODULE_LOG_TAG, "Crated Kodein module: $name")
    return Kodein.Module(name, false, "${BuildConfig.MODULE_SHORT_NAME}-") {}
}