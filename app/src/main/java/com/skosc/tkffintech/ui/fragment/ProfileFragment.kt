package com.skosc.tkffintech.ui.fragment


import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.skosc.tkffintech.R
import com.skosc.tkffintech.ui.adapter.ProfileAttributeAdapter
import com.skosc.tkffintech.ui.model.UserInfoAttribute
import com.skosc.tkffintech.ui.view.UserInfoSectionCard
import com.skosc.tkffintech.viewmodel.profile.ProfileViewModel
import kotlinx.android.synthetic.main.card_profile_stats.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : TKFFragment() {

    val vm by lazy { getViewModel(ProfileViewModel::class) }

    val contactInfoCard by lazy {
        UserInfoSectionCard(context!!).apply {
            headerText = "Contact Info"
            iconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_contacts)!!
            recycler.adapter = ProfileAttributeAdapter()
        }
    }

    val schoolInfoCard by lazy {
        UserInfoSectionCard(context!!).apply {
            headerText = "School Info"
            iconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_education)!!
            recycler.adapter = ProfileAttributeAdapter()
        }
    }

    val workInfoCard by lazy {
        UserInfoSectionCard(context!!).apply {
            headerText = "Work Info"
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
        profile_info_cards.addView(contactInfoCard)
        profile_info_cards.addView(schoolInfoCard)
        profile_info_cards.addView(workInfoCard)

        vm.fullName.observe(this, Observer {
            profile_name.text = it
        })

        vm.shortInfo.observe(this, Observer {
            profile_info.text = it
        })

        vm.statsScore.observe(this, Observer {
            profile_stats_score.text = it.toString()
        })

        vm.statsTests.observe(this, Observer {
            profile_stats_curse.text = it.toString()
        })

        vm.statsCourses.observe(this, Observer {
            profile_stats_score.text = it.toString()
        })

        vm.quote.observe(this, Observer {
            profile_quote.text = it
        })

        vm.contactInfo.observe(this, Observer {
            val entries = it.entries.map { entry -> UserInfoAttribute(entry.key, entry.value) }
            val adapter = contactInfoCard.recycler.adapter as ProfileAttributeAdapter
            adapter.items = entries
            adapter.notifyDataSetChanged()
        })

        vm.schoolInfo.observe(this, Observer {
            val entries = it.entries.map { entry -> UserInfoAttribute(entry.key, entry.value) }
            val adapter = schoolInfoCard.recycler.adapter as ProfileAttributeAdapter
            adapter.items = entries
            adapter.notifyDataSetChanged()
        })

        vm.workInfo.observe(this, Observer {
            val entries = it.entries.map { entry -> UserInfoAttribute(entry.key, entry.value) }
            val adapter = workInfoCard.recycler.adapter as ProfileAttributeAdapter
            adapter.items = entries
            adapter.notifyDataSetChanged()
        })
    }
}
