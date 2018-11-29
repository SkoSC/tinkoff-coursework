package com.skosc.tkffintech.viewmodel.eventdetail

import com.skosc.tkffintech.viewmodel.TKFViewModelFactory
import com.skosc.tkffintech.viewmodel.ViewModelArgs
import org.kodein.di.Kodein

class EventDetailViewModelFactory(kodein: Kodein, private val args: ViewModelArgs) : TKFViewModelFactory<EventDetailViewModel>(kodein) {
    override val key: String = args.hashCode().toString()

    override fun create(): EventDetailViewModel {
        return EventDetailViewModelImpl(
                args[EventDetailViewModel.ARG_HID]?.toLong() ?: 0L,
                inject(),
                inject()
        )
    }
}