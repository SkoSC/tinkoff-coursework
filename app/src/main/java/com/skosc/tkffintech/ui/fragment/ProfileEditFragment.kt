package com.skosc.tkffintech.ui.fragment


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skosc.tkffintech.R
import com.skosc.tkffintech.entities.ProfileAttributes
import com.skosc.tkffintech.misc.model.ProfileField
import com.skosc.tkffintech.misc.model.ProfileFieldFactory
import com.skosc.tkffintech.ui.adapter.ProfileAttributeEditAdapter
import com.skosc.tkffintech.ui.view.UserInfoSectionCard
import com.skosc.tkffintech.utils.extensions.addViews
import com.skosc.tkffintech.utils.extensions.getDrawableCompat
import com.skosc.tkffintech.viewmodel.profile.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile_edit.*

class ProfileEditFragment : TKFFragment() {

    val vm by lazy { getViewModel(ProfileViewModel::class) }

    private val contactInfoCard by lazy {
        UserInfoSectionCard(context!!).apply {
            headerText = context.getString(R.string.profile_attributes_card_contact)
            iconDrawable = context.getDrawableCompat(R.drawable.ic_contacts)!!
            recycler.adapter = ProfileAttributeEditAdapter()
        }
    }

    private val schoolInfoCard by lazy {
        UserInfoSectionCard(context!!).apply {
            headerText = context.getString(R.string.profile_attributes_card_school)
            iconDrawable = context.getDrawable(R.drawable.ic_education)!!
            recycler.adapter = ProfileAttributeEditAdapter()
        }
    }

    private val workInfoCard by lazy {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDataUpdateListener()
        setupPrimaryInfo()
        setupInfoCards()

        profile_edit_apply_btn.setOnClickListener(this::showPlaceholderDialog)
    }

    private fun setupDataUpdateListener() {
        profile_info_cards.addViews(contactInfoCard, schoolInfoCard, workInfoCard)
    }

    private fun setupPrimaryInfo() {
        vm.fullName.observe(this, Observer {
            profile_name.text = it
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

    private fun userInfoAttributesObserver(card: UserInfoSectionCard): Observer<List<ProfileField>> {
        return Observer { fields ->
            val adapter = card.recycler.adapter as ProfileAttributeEditAdapter
            adapter.submitItems(fields)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (view != null) {
            (view as ViewGroup).removeAllViews()
        }
    }

    @Deprecated("Can't properly access network")
    private fun onApplyButtonClicked(v: View) {
        val fields = listOf(contactInfoCard, schoolInfoCard, workInfoCard)
                .map { it.recycler.adapter as ProfileAttributeEditAdapter }
                .map { it.items }
                .map(this::addDescriptionField)
                .reduce { l, r -> l + r }
        vm.changeInfo(fields)
    }

    private fun addDescriptionField(fields: List<ProfileField>): List<ProfileField> {
        val descriptionValue = vm.quote.value ?: ""
        val name = ProfileFieldFactory.lookup(ProfileAttributes.FIELD_DESCRIPTION).make(descriptionValue)

        return fields + name
    }

    private fun showPlaceholderDialog(v: View) {
        AlertDialog.Builder(context)
                .setTitle(getString(R.string.error_not_implemented))
                .setMessage(getString(R.string.profile_edit_not_impl_msg))
                .setPositiveButton("") { _, _ -> }
                .create()
                .show()
    }
}
