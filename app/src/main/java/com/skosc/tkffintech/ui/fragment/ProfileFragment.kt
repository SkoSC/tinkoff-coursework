package com.skosc.tkffintech.ui.fragment


import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.skosc.tkffintech.R
import com.skosc.tkffintech.ui.adapter.ProfileAttributeAdapter
import com.skosc.tkffintech.ui.view.UserInfoSectionCard
import com.skosc.tkffintech.viewmodel.profile.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : TKFFragment() {

    val vm by lazy { getViewModel(ProfileViewModel::class) }

    val contactInfoCard by lazy {
        UserInfoSectionCard(context!!).apply {
            headerText = "Contact Info"
            iconDrawable = ColorDrawable(R.color.colorAccent)
            recycler.layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
            recycler.adapter = ProfileAttributeAdapter()
        }
    }

    val schoolInfoCard by lazy {
        UserInfoSectionCard(context!!).apply {
            headerText = "School Info"
            iconDrawable = ColorDrawable(R.color.colorAccent)
            recycler.layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
            recycler.adapter = ProfileAttributeAdapter()
        }
    }

    val workInfoCard by lazy {
        UserInfoSectionCard(context!!).apply {
            headerText = "Work Info"
            iconDrawable = ColorDrawable(R.color.colorAccent)
            recycler.layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
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
    }
}
