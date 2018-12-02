package com.skosc.tkffintech.viewmodel.profile

import androidx.lifecycle.LiveData
import com.skosc.tkffintech.misc.Trigger
import com.skosc.tkffintech.misc.model.ProfileField
import com.skosc.tkffintech.misc.model.UpdateResult
import com.skosc.tkffintech.viewmodel.RxViewModel

abstract class ProfileViewModel : RxViewModel() {
    abstract val fullName: LiveData<String>
    abstract val shortInfo: LiveData<String>
    abstract val avatarUrl: LiveData<String>
    abstract val statsScore: LiveData<String>
    abstract val statsTests: LiveData<String>
    abstract val statsCourses: LiveData<String>
    abstract val quote: LiveData<String>

    abstract val contactInfo: LiveData<List<ProfileField>>
    abstract val schoolInfo: LiveData<List<ProfileField>>
    abstract val workInfo: LiveData<List<ProfileField>>

    abstract fun changeInfo(fields: List<ProfileField>): LiveData<UpdateResult>

    abstract val dataUpdated: Trigger

    abstract fun signout()
    abstract fun update()
}