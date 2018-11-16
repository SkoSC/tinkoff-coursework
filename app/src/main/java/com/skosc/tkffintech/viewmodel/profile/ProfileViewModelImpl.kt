package com.skosc.tkffintech.viewmodel.profile

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.UserInfo
import com.skosc.tkffintech.entities.UserInfoAttributes
import com.skosc.tkffintech.usecase.LoadCurrentUserInfo
import com.skosc.tkffintech.usecase.PerformLogout
import com.skosc.tkffintech.usecase.StatisticsCalculator
import com.skosc.tkffintech.utils.own
import io.reactivex.android.schedulers.AndroidSchedulers

class ProfileViewModelImpl(
        private val loadCurrentUserInfo: LoadCurrentUserInfo,
        private val performLogout: PerformLogout,
        private val statisticsCalculator: StatisticsCalculator) : ProfileViewModel() {

    override val fullName: MutableLiveData<String> = MutableLiveData()
    override val shortInfo: MutableLiveData<String> = MutableLiveData()
    override val avatarUrl: MutableLiveData<String> = MutableLiveData()
    override val statsScore: MutableLiveData<Double> = MutableLiveData()
    override val statsTests: MutableLiveData<Int> = MutableLiveData()
    override val statsCourses: MutableLiveData<Int> = MutableLiveData()
    override val quote: MutableLiveData<String> = MutableLiveData()

    override val contactInfo: MutableLiveData<Map<Int, String>> = MutableLiveData()
    override val schoolInfo: MutableLiveData<Map<Int, String>> = MutableLiveData()
    override val workInfo: MutableLiveData<Map<Int, String>> = MutableLiveData()

    override val dataUpdated: MutableLiveData<*> = MutableLiveData<Unit>()

    private val bindUserInfoToLiveData: (UserInfo) -> Unit = { info ->
        dataUpdated.value = Unit // Trigger
        fullName.value = "${info.firstName} ${info.lastName}"
        shortInfo.value = info.email
        avatarUrl.value = "https://fintech.tinkoff.ru${info.avatar}"
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


    init {
        cdisp own loadCurrentUserInfo.currentUserInfo
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bindUserInfoToLiveData)

        cdisp own statisticsCalculator.totalScore
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    statsScore.value = it
                }

        cdisp own statisticsCalculator.totalTests
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    statsTests.value = it
                }

        cdisp own statisticsCalculator.totalCourses
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    statsCourses.value = it
                }
    }

    override fun signout() {
        performLogout.perform()
    }

    override fun update() {
        loadCurrentUserInfo.tryLoadUserInfoFromNetwork()
    }
}