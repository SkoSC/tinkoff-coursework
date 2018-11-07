package com.skosc.tkffintech.viewmodel.profile

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.UserInfoAttributes
import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.utils.observeOnMainThread
import com.skosc.tkffintech.utils.own

class ProfileViewModelImpl(val currentUserRepo: CurrentUserRepo) : ProfileViewModel() {
    override val fullName: MutableLiveData<String> = MutableLiveData()
    override val shortInfo: MutableLiveData<String> = MutableLiveData()
    override val statsScore: MutableLiveData<Double> = MutableLiveData()
    override val statsTests: MutableLiveData<Int> = MutableLiveData()
    override val statsCourses: MutableLiveData<Int> = MutableLiveData()
    override val quote: MutableLiveData<String> = MutableLiveData()

    override val contactInfo: MutableLiveData<Map<Int, String>> = MutableLiveData()
    override val schoolInfo: MutableLiveData<Map<Int, String>> = MutableLiveData()
    override val workInfo: MutableLiveData<Map<Int, String>> = MutableLiveData()


    init {
        refresh()
    }

    override fun signout() {
        currentUserRepo.signout()
    }

    override fun refresh() {
        cdisp own currentUserRepo.info
                .observeOnMainThread()
                .subscribe { info ->
                    fullName.value = "${info.firstName} ${info.lastName}"
                    shortInfo.value = info.email
                    statsScore.value = 23.4
                    statsTests.value = 7
                    statsCourses.value = 2
                    quote.value = info.description

                    contactInfo.value = mapOf(
                            UserInfoAttributes.FIELD_MOBILE_PHONE to info.phoneMobile,
                            UserInfoAttributes.FIELD_EMAIL to info.email,
                            UserInfoAttributes.FIELD_HOME_CITY to info.region
                    )

                    schoolInfo.value = mapOf(
                            UserInfoAttributes.FIELD_SCHOOL to info.school,
                            UserInfoAttributes.FIELD_SCHOOL_GRADUATION to info.schoolGraduation.toString(),
                            UserInfoAttributes.FIELD_UNIVERSITY to info.university,
                            UserInfoAttributes.FIELD_FACILITY to info.faculty,
                            UserInfoAttributes.FIELD_DEPARTMENT to info.department,
                            UserInfoAttributes.FIELD_UNIVERSITY_GRADUATION to info.universityGraduation.toString()
                    )
                    workInfo.value = mapOf(
                            UserInfoAttributes.FIELD_WORKPLACE to info.currentWork
                    )
                }
    }
}