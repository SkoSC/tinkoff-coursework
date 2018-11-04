package com.skosc.tkffintech.ui.model

import androidx.annotation.IntRange
import androidx.annotation.StringRes
import com.skosc.tkffintech.R
import com.skosc.tkffintech.entities.UserInfoAttributes
import com.skosc.tkffintech.entities.UserInfoAttributes.FIELD_MOBILE_PHONE
import com.skosc.tkffintech.entities.UserInfoAttributes.FIELD_WORKPLACE
import java.lang.RuntimeException

data class UserInfoAttribute(
        @IntRange(from = FIELD_MOBILE_PHONE.toLong(), to = FIELD_WORKPLACE.toLong()) val field: Int,
        val value: String
) {
    @StringRes
    val fieldName: Int = when (field) {
        UserInfoAttributes.FIELD_MOBILE_PHONE -> R.string.profile_attribute_mobile_phone
        UserInfoAttributes.FIELD_EMAIL -> R.string.profile_attribute_email
        UserInfoAttributes.FIELD_HOME_CITY -> R.string.profile_attribute_home_city
        UserInfoAttributes.FIELD_SCHOOL -> R.string.profile_attribute_school
        UserInfoAttributes.FIELD_SCHOOL_GRADUATION -> R.string.profile_attribute_school_grad
        UserInfoAttributes.FIELD_UNIVERSITY -> R.string.profile_attribute_university
        UserInfoAttributes.FIELD_FACILITY -> R.string.profile_attribute_facility
        UserInfoAttributes.FIELD_UNIVERSITY_GRADUATION -> R.string.profile_attribute_university
        UserInfoAttributes.FIELD_DEPARTMENT -> R.string.profile_attribute_department
        UserInfoAttributes.FIELD_WORKPLACE -> R.string.profile_attribute_wrokplace
        else -> throw RuntimeException("Unsupported user profile attribute with id: $field")
    }
}