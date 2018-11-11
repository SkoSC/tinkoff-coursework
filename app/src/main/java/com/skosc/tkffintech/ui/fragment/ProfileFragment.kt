package com.skosc.tkffintech.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.skosc.tkffintech.R
import com.skosc.tkffintech.ui.adapter.ProfileAttributeAdapter
import com.skosc.tkffintech.ui.model.UserInfoAttribute
import com.skosc.tkffintech.ui.view.UserInfoSectionCard
import com.skosc.tkffintech.utils.addViews
import com.skosc.tkffintech.viewmodel.profile.ProfileViewModel
import kotlinx.android.synthetic.main.card_profile_stats.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : TKFFragment() {

    val vm by lazy { getViewModel(ProfileViewModel::class) }

    val contactInfoCard by lazy {
        UserInfoSectionCard(context!!).apply {
            headerText = context.getString(R.string.profile_attributes_card_contact)
            iconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_contacts)!!
            recycler.adapter = ProfileAttributeAdapter()
        }
    }

    val schoolInfoCard by lazy {
        UserInfoSectionCard(context!!).apply {
            headerText = context.getString(R.string.profile_attributes_card_school)
            iconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_education)!!
            recycler.adapter = ProfileAttributeAdapter()
        }
    }

    val workInfoCard by lazy {
        UserInfoSectionCard(context!!).apply {
            headerText = context.getString(R.string.profile_attributes_card_work)
            iconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_work)!!
            recycler.adapter = ProfileAttributeAdapter()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override fun onStart() {
        super.onStart()
        profile_info_cards.addViews(contactInfoCard, schoolInfoCard, workInfoCard)

        vm.dataUpdated.observe(this, Observer {
            profile_refresh.isRefreshing = false
        })

        vm.fullName.observe(this, Observer {
            profile_name.text = it
        })

        vm.shortInfo.observe(this, Observer {
            profile_info.text = it
        })

        vm.avatarUrl.observe(this, Observer {
            Glide.with(context!!)
                    .load(it)
                    .apply(RequestOptions.fitCenterTransform().circleCrop())
                    .into(profile_avatar)
        })

        vm.statsScore.observe(this, Observer {
            profile_stats_score.text = it.toString()
        })

        vm.statsTests.observe(this, Observer {
            profile_stats_tests.text = it.toString()
        })

        vm.statsCourses.observe(this, Observer {
            profile_stats_curse.text = it.toString()
        })

        vm.quote.observe(this, Observer {
            profile_quote.text = it
        })

        vm.contactInfo.observe(this, userInfoAttributesObserver(contactInfoCard))
        vm.schoolInfo.observe(this, userInfoAttributesObserver(schoolInfoCard))
        vm.workInfo.observe(this, userInfoAttributesObserver(workInfoCard))

        profile_signout_btn.setOnClickListener {
            vm.signout()
        }

        profile_refresh.setOnRefreshListener {
            vm.update()
        }
    }

    private fun userInfoAttributesObserver(card: UserInfoSectionCard): Observer<Map<Int, String>> {
        return Observer {
            val entries = it.entries.map(UserInfoAttribute.Companion::from)
            val adapter = card.recycler.adapter as ProfileAttributeAdapter
            adapter.items = entries
            adapter.notifyDataSetChanged()
        }
    }
}
