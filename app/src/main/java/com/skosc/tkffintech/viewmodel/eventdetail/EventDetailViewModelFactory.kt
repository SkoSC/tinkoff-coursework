package com.skosc.tkffintech.viewmodel.eventdetail

import com.skosc.tkffintech.viewmodel.TKFViewModelFactory
import org.kodein.di.Kodein

class EventDetailViewModelFactory(kodein: Kodein) : TKFViewModelFactory<EventDetailViewModel>(kodein) {
    override fun create(): EventDetailViewModel {
        return EventDetailViewModelImpl(inject())
    }
}