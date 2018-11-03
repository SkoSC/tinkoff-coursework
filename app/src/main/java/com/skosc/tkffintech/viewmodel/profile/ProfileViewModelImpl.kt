package com.skosc.tkffintech.viewmodel.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.utils.observeOnMainThread
import com.skosc.tkffintech.utils.own
import com.skosc.tkffintech.viewmodel.RxViewModel

class ProfileViewModelImpl(val currentUserRepo: CurrentUserRepo) : ProfileViewModel() {
    override val fullName: MutableLiveData<String> = MutableLiveData()

    init {
        cdisp own currentUserRepo.info
                .observeOnMainThread()
                .subscribe ({ userInfo ->
                    fullName.value = "%s %s".format(userInfo.firstName, userInfo.lastName)
                })
    }
}