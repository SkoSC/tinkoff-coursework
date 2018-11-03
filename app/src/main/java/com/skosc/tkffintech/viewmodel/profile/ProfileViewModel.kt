package com.skosc.tkffintech.viewmodel.profile

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.viewmodel.RxViewModel

abstract class ProfileViewModel : RxViewModel() {
    abstract val fullName: LiveData<String>
}