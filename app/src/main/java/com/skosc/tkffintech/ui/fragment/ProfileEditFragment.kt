package com.skosc.tkffintech.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.skosc.tkffintech.R
import com.skosc.tkffintech.misc.ProfileFieldFactory
import com.skosc.tkffintech.ui.adapter.ProfileAttributeEditAdapter
import com.skosc.tkffintech.ui.view.UserInfoSectionCard
import com.skosc.tkffintech.utils.extensions.addViews
import com.skosc.tkffintech.utils.extensions.getDrawableCompat
import com.skosc.tkffintech.viewmodel.profile.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile_edit.*

class ProfileEditFragment : TKFFragment() {

    val vm by lazy { getViewModel(ProfileViewModel::class) }

    val contactInfoCard by lazy {
        UserInfoSectionCard(context!!).apply {
            headerText = context.getString(R.string.profile_attributes_card_contact)
            iconDrawable = context.getDrawableCompat(R.drawable.ic_contacts)!!
            recycler.adapter = ProfileAttributeEditAdapter()
        }
    }

    val schoolInfoCard by lazy {
        UserInfoSectionCard(context!!).apply {
            headerText = context.getString(R.string.profile_attributes_card_school)
            iconDrawable = context.getDrawable(R.drawable.ic_education)!!
            recycler.adapter = ProfileAttributeEditAdapter()
        }
    }

    val workInfoCard by lazy {
        UserInfoSectionCard(context!!).apply {
            headerText = context.getString(R.string.profile_attributes_card_work)
            iconDrawable = context.getDrawableCompat(R.drawable.ic_work)!!
            recycler.adapter = ProfileAttributeEditAdapter()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile_edit, container, false)
    }


    override fun onStart() {
        super.onStart()
        setupDataUpdateListener()
        setupPrimaryInfo()
        setupInfoCards()

        profile_edit_apply_btn.setOnClickListener(this::onApplyButtonClicked)
    }

    private fun setupDataUpdateListener() {
        profile_info_cards.addViews(contactInfoCard, schoolInfoCard, workInfoCard)
    }

    private fun setupPrimaryInfo() {
        vm.fullName.observe(this, Observer {
            profile_name.setText(it)
        })

        vm.avatarUrl.observe(this, Observer {
            Glide.with(context!!)
                    .load(it)
                    .apply(RequestOptions.fitCenterTransform().circleCrop())
                    .into(profile_avatar)
        })

        vm.quote.observe(this, Observer {
            profile_quote.setText(it)
        })
    }

    private fun setupInfoCards() {
        vm.contactInfo.observe(this, userInfoAttributesObserver(contactInfoCard))
        vm.schoolInfo.observe(this, userInfoAttributesObserver(schoolInfoCard))
        vm.workInfo.observe(this, userInfoAttributesObserver(workInfoCard))
    }

    private fun userInfoAttributesObserver(card: UserInfoSectionCard): Observer<Map<Int, String>> {
        return Observer {
            val entries = it.entries.map { ProfileFieldFactory.lookup(it.key).make(it.value) }
            val adapter = card.recycler.adapter as ProfileAttributeEditAdapter
            adapter.submitItems(entries)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (view != null) {
            (view as ViewGroup).removeAllViews()
        }
    }

    private fun onApplyButtonClicked(v: View) {
        val fiedls = listOf(contactInfoCard, schoolInfoCard, workInfoCard)
                .map { it.recycler.adapter as ProfileAttributeEditAdapter }
                .map { it.items }
                .reduce { l, r -> l + r }
        vm.changeInfo(fiedls)
    }
}
