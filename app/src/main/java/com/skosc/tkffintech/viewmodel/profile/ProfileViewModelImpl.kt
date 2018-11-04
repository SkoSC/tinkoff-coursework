package com.skosc.tkffintech.viewmodel.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.entities.UserInfoAttributes
import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.utils.observeOnMainThread
import com.skosc.tkffintech.utils.own
import com.skosc.tkffintech.viewmodel.RxViewModel

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
        fullName.value = "Alexey Jakovlev"
        shortInfo.value = "scorpius98.alex@gmail.com"
        statsScore.value = 23.4
        statsTests.value = 7
        statsCourses.value = 2
        quote.value = "Program ping pong bing bong bong! Go JAVA! FOR LIVE! YO!"

        contactInfo.value = mapOf(
                UserInfoAttributes.FIELD_MOBILE_PHONE to "+7-914-215-50-71",
                UserInfoAttributes.FIELD_EMAIL to "scorpius98.alex@gmail.com",
                UserInfoAttributes.FIELD_HOME_CITY to "Moscow"
        )

        schoolInfo.value = mapOf(
                UserInfoAttributes.FIELD_SCHOOL to "School 80",
                UserInfoAttributes.FIELD_SCHOOL_GRADUATION to "2016",
                UserInfoAttributes.FIELD_UNIVERSITY to "MIREA",
                UserInfoAttributes.FIELD_FACILITY to "IT",
                UserInfoAttributes.FIELD_DEPARTMENT to "MOSIT",
                UserInfoAttributes.FIELD_UNIVERSITY_GRADUATION to "2021"
        )
        workInfo.value = mapOf(
                UserInfoAttributes.FIELD_WORKPLACE to "Tinkoff"
        )
    }
}