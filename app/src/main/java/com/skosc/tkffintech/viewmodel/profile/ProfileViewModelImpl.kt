package com.skosc.tkffintech.viewmodel.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.ProfileAttributes
import com.skosc.tkffintech.entities.UserInfo
import com.skosc.tkffintech.misc.ProfileField
import com.skosc.tkffintech.misc.Trigger
import com.skosc.tkffintech.misc.UpdateResult
import com.skosc.tkffintech.usecase.LoadCurrentUserInfo
import com.skosc.tkffintech.usecase.LoadCurrentUserStatistics
import com.skosc.tkffintech.usecase.PerformLogout
import com.skosc.tkffintech.usecase.UpdateUserInfo
import com.skosc.tkffintech.utils.extensions.own
import com.skosc.tkffintech.utils.formatting.NumberFormatter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProfileViewModelImpl(
        private val loadCurrentUserInfo: LoadCurrentUserInfo,
        private val statistics: LoadCurrentUserStatistics,
        private val performLogout: PerformLogout,
        private val updateUserInfo: UpdateUserInfo
) : ProfileViewModel() {

    override val fullName: MutableLiveData<String> = MutableLiveData()
    override val shortInfo: MutableLiveData<String> = MutableLiveData()
    override val avatarUrl: MutableLiveData<String> = MutableLiveData()
    override val statsScore: MutableLiveData<String> = MutableLiveData()
    override val statsTests: MutableLiveData<String> = MutableLiveData()
    override val statsCourses: MutableLiveData<String> = MutableLiveData()
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

        cdisp own statistics.bundled()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { stats ->
                    statsCourses.value = "${stats.coursesCount}+"
                    statsTests.value = "${stats.testsCount}+"
                    statsScore.value = "${NumberFormatter.userScore(stats.totalScore)}+"
                }
    }

    override fun signout() {
        performLogout.perform()
    }

    override fun update() {
        loadCurrentUserInfo.tryLoadUserInfoFromNetwork()
    }

    override fun changeInfo(fields: List<ProfileField>): LiveData<UpdateResult> {
        val indicator = MutableLiveData<UpdateResult>()

        cdisp own loadCurrentUserInfo.currentUserInfo
                .map {
                    var info = it
                    fields.forEach { field ->
                        info = field.modify(info)
                    }
                    info
                }
                .flatMapSingle { updateUserInfo.update(it) }
                .subscribe { indicator.value = it }

        return indicator
    }
}