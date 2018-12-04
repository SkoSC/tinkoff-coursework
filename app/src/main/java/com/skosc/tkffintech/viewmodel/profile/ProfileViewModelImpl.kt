package com.skosc.tkffintech.viewmodel.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.ProfileAttributes
import com.skosc.tkffintech.entities.UserInfo
import com.skosc.tkffintech.misc.Trigger
import com.skosc.tkffintech.misc.model.ProfileField
import com.skosc.tkffintech.misc.model.ProfileFieldFactory
import com.skosc.tkffintech.misc.model.UpdateResult
import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.usecase.LoadCurrentUserStatistics
import com.skosc.tkffintech.usecase.PerformLogout
import com.skosc.tkffintech.utils.extensions.own
import com.skosc.tkffintech.utils.formatting.NumberFormatter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProfileViewModelImpl(
        private val userRepo: CurrentUserRepo,
        statistics: LoadCurrentUserStatistics,
        private val performLogout: PerformLogout
) : ProfileViewModel() {

    override val fullName: MutableLiveData<String> = MutableLiveData()
    override val shortInfo: MutableLiveData<String> = MutableLiveData()
    override val avatarUrl: MutableLiveData<String> = MutableLiveData()
    override val statsScore: MutableLiveData<String> = MutableLiveData()
    override val statsTests: MutableLiveData<String> = MutableLiveData()
    override val statsCourses: MutableLiveData<String> = MutableLiveData()
    override val quote: MutableLiveData<String> = MutableLiveData()

    override val contactInfo: MutableLiveData<List<ProfileField>> = MutableLiveData()
    override val schoolInfo: MutableLiveData<List<ProfileField>> = MutableLiveData()
    override val workInfo: MutableLiveData<List<ProfileField>> = MutableLiveData()

    override val dataUpdated: Trigger = Trigger()

    private val bindUserInfoToLiveData: (UserInfo) -> Unit = { info ->
        dataUpdated.fire()
        fullName.value = "${info.firstName} ${info.lastName}"
        shortInfo.value = info.email
        avatarUrl.value = "https://fintech.tinkoff.ru${info.avatar}"
        quote.value = info.description

        contactInfo.value = listOf(
                ProfileFieldFactory.lookup(ProfileAttributes.FIELD_MOBILE_PHONE).make(info.phoneMobile),
                ProfileFieldFactory.lookup(ProfileAttributes.FIELD_EMAIL).make(info.email),
                ProfileFieldFactory.lookup(ProfileAttributes.FIELD_HOME_CITY).make(info.region)
        )

        schoolInfo.value = listOf(
                ProfileFieldFactory.lookup(ProfileAttributes.FIELD_SCHOOL).make(info.school),
                ProfileFieldFactory.lookup(ProfileAttributes.FIELD_SCHOOL_GRADUATION).make(info.schoolGraduation.toString()),
                ProfileFieldFactory.lookup(ProfileAttributes.FIELD_UNIVERSITY).make(info.university),
                ProfileFieldFactory.lookup(ProfileAttributes.FIELD_FACILITY).make(info.faculty),
                ProfileFieldFactory.lookup(ProfileAttributes.FIELD_DEPARTMENT).make(info.department),
                ProfileFieldFactory.lookup(ProfileAttributes.FIELD_UNIVERSITY_GRADUATION).make(info.universityGraduation.toString())
        )

        workInfo.value = listOf(
                ProfileFieldFactory.lookup(ProfileAttributes.FIELD_WORKPLACE).make(info.currentWork)
        )
    }


    init {
        cdisp own userRepo.info
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
        userRepo.forceRefreshUserInfo()
    }

    override fun changeInfo(fields: List<ProfileField>): LiveData<UpdateResult> {
        val indicator = MutableLiveData<UpdateResult>()

        cdisp own userRepo.info
                .map {
                    var info = it
                    fields.forEach { field ->
                        info = field.modify(info)
                    }
                    info
                }
                .flatMapSingle { userRepo.update(it) }
                .subscribe { indicator.value = it }

        return indicator
    }
}