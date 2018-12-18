package com.skosc.tkffintech.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skosc.tkffintech.R
import com.skosc.tkffintech.misc.model.ProfileField
import com.skosc.tkffintech.ui.adapter.ProfileAttributeAdapter
import com.skosc.tkffintech.ui.view.UserInfoSectionCard
import com.skosc.tkffintech.utils.extensions.addViews
import com.skosc.tkffintech.utils.extensions.getDrawableCompat
import com.skosc.tkffintech.viewmodel.profile.ProfileViewModel
import kotlinx.android.synthetic.main.card_profile_stats.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : TKFFragment() {

    private val navController by lazy { Navigation.findNavController(profile_info_cards) }
    private val vm by lazy { getViewModel(ProfileViewModel::class) }

    private val contactInfoCard by lazy {
        UserInfoSectionCard(context!!).apply {
            headerText = context.getString(R.string.profile_attributes_card_contact)
            iconDrawable = context.getDrawableCompat(R.drawable.ic_contacts)!!
            recycler.adapter = ProfileAttributeAdapter()
        }
    }

    private val schoolInfoCard by lazy {
        UserInfoSectionCard(context!!).apply {
            headerText = context.getString(R.string.profile_attributes_card_school)
            iconDrawable = context.getDrawable(R.drawable.ic_education)!!
            recycler.adapter = ProfileAttributeAdapter()
        }
    }

    private val workInfoCard by lazy {
        UserInfoSectionCard(context!!).apply {
            headerText = context.getString(R.string.profile_attributes_card_work)
            iconDrawable = context.getDrawableCompat(R.drawable.ic_work)!!
            recycler.adapter = ProfileAttributeAdapter()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profile_info_cards.addViews(contactInfoCard, schoolInfoCard, workInfoCard)
        setupPrimaryInfo()
        setupStatisticsCard()
        setupInfoCards()
        setupBottomButtons()
    }

    override fun onStop() {
        super.onStop()
        profile_info_cards.removeAllViews()
    }

    private fun setupPrimaryInfo() {
        vm.dataUpdated.observe(this) {
            profile_refresh.isRefreshing = false
        }

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

        vm.quote.observe(this, Observer {
            profile_quote.text = it
        })
    }

    private fun setupBottomButtons() {
        profile_signout_btn.setOnClickListener {
            vm.signout()
        }

        profile_refresh.setOnRefreshListener {
            vm.update()
        }

        profile_edit_btn.setOnClickListener {
            navController.navigate(R.id.navigation_profile_edit)
        }
    }

    private fun setupInfoCards() {
        vm.contactInfo.observe(this, userInfoAttributesObserver(contactInfoCard))
        vm.schoolInfo.observe(this, userInfoAttributesObserver(schoolInfoCard))
        vm.workInfo.observe(this, userInfoAttributesObserver(workInfoCard))
    }

    private fun setupStatisticsCard() {
        card_stats_left_slot_text.text = getString(R.string.stats_score)
        vm.statsScore.observe(this, Observer { scoreSum ->
            card_stats_left_slot.text = scoreSum
        })

        card_stats_center_slot_text.text = getString(R.string.stats_tests)
        vm.statsTests.observe(this, Observer {
            card_stats_center_slot.text = it
        })

        card_stats_right_slot_text.text = getString(R.string.stats_courses)
        vm.statsCourses.observe(this, Observer {
            card_stats_right_slot.text = it
        })
    }

    private fun userInfoAttributesObserver(card: UserInfoSectionCard): Observer<List<ProfileField>> {
        return Observer { fields ->
            val adapter = card.recycler.adapter as ProfileAttributeAdapter
            adapter.submitItems(fields)
        }
    }
}
