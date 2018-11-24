package com.skosc.tkffintech.viewmodel.profile

import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.UserInfo
import com.skosc.tkffintech.entities.ProfileAttributes
import com.skosc.tkffintech.usecase.LoadCurrentUserInfo
import com.skosc.tkffintech.usecase.PerformLogout
import com.skosc.tkffintech.utils.Trigger
import com.skosc.tkffintech.utils.own
import io.reactivex.android.schedulers.AndroidSchedulers

class ProfileViewModelImpl(
        private val loadCurrentUserInfo: LoadCurrentUserInfo,
        private val performLogout: PerformLogout
) : ProfileViewModel() {

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

    override val dataUpdated: Trigger = Trigger()

    private val bindUserInfoToLiveData: (UserInfo) -> Unit = { info ->
        dataUpdated.trigger()
        fullName.value = "${info.firstName} ${info.lastName}"
        shortInfo.value = info.email
        avatarUrl.value = "https://fintech.tinkoff.ru${info.avatar}"
        quote.value = info.description

        contactInfo.value = mapOf(
                ProfileAttributes.FIELD_MOBILE_PHONE to info.phoneMobile,
                ProfileAttributes.FIELD_EMAIL to info.email,
                ProfileAttributes.FIELD_HOME_CITY to info.region
        )

        schoolInfo.value = mapOf(
                ProfileAttributes.FIELD_SCHOOL to info.school,
                ProfileAttributes.FIELD_SCHOOL_GRADUATION to info.schoolGraduation.toString(),
                ProfileAttributes.FIELD_UNIVERSITY to info.university,
                ProfileAttributes.FIELD_FACILITY to info.faculty,
                ProfileAttributes.FIELD_DEPARTMENT to info.department,
                ProfileAttributes.FIELD_UNIVERSITY_GRADUATION to info.universityGraduation.toString()
        )
        workInfo.value = mapOf(
                ProfileAttributes.FIELD_WORKPLACE to info.currentWork
        )
    }


    init {
        cdisp own loadCurrentUserInfo.currentUserInfo
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    bindUserInfoToLiveData(it)
                }
    }

    override fun signout() {
        performLogout.perform()
    }

    override fun update() {
        loadCurrentUserInfo.tryLoadUserInfoFromNetwork()
    }
}