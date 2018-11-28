package com.skosc.tkffintech.misc.model

import androidx.annotation.StringRes
import com.skosc.tkffintech.R
import com.skosc.tkffintech.entities.ProfileAttributes
import com.skosc.tkffintech.entities.UserInfo
import com.skosc.tkffintech.utils.cantorPairing

class ProfileField(
        var value: String,
        @StringRes val header: Int,
        private val apply: (UserInfo, String) -> UserInfo,
        private val validate: (String) -> Boolean = { true }
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProfileField

        if (value != other.value) return false
        if (header != other.header) return false

        return true
    }

    override fun hashCode(): Int {
        return cantorPairing(value.hashCode(), header.hashCode())
    }

    fun modify(info: UserInfo): UserInfo = apply(info, value)

    fun withValue(value: String) = ProfileField(value, header, apply, validate)
}

class ProfileFieldFactory(
        @StringRes private val header: Int,
        private val apply: (UserInfo, String) -> UserInfo,
        private val validate: (String) -> Boolean = { true }
) {
    companion object {
        private val phoneRegex = Regex("^(\\+[1-9][0-9]*(\\([0-9]*\\)|-[0-9]*-))?[0]?[1-9][0-9\\- ]*\$\n")
        private val emailRegex = Regex("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])")

        fun lookup(field: Int): ProfileFieldFactory {
            return when (field) {
                ProfileAttributes.FIELD_MOBILE_PHONE -> ProfileFieldFactory(
                        R.string.profile_attribute_mobile_phone,
                        { info, value -> info.copy(phoneMobile = value) },
                        { value -> phoneRegex.matches(value) }
                )
                ProfileAttributes.FIELD_EMAIL -> ProfileFieldFactory(
                        R.string.profile_attribute_email,
                        { info, value -> info.copy(email = value) },
                        { value -> emailRegex.matches(value) }
                )
                ProfileAttributes.FIELD_HOME_CITY -> ProfileFieldFactory(
                        R.string.profile_attribute_home_city,
                        { info, value -> info.copy(region = value) }
                )
                ProfileAttributes.FIELD_SCHOOL -> ProfileFieldFactory(
                        R.string.profile_attribute_school,
                        { info, value -> info.copy(school = value) }
                )
                ProfileAttributes.FIELD_SCHOOL_GRADUATION -> ProfileFieldFactory(
                        R.string.profile_attribute_school_grad,
                        { info, value -> info.copy(schoolGraduation = value.toInt()) },
                        { value -> value.toIntOrNull() ?: -1 > 0 }
                )
                ProfileAttributes.FIELD_UNIVERSITY -> ProfileFieldFactory(
                        R.string.profile_attribute_university,
                        { info, value -> info.copy(university = value) }
                )
                ProfileAttributes.FIELD_FACILITY -> ProfileFieldFactory(
                        R.string.profile_attribute_facility,
                        { info, value -> info.copy(faculty = value) }
                )
                ProfileAttributes.FIELD_UNIVERSITY_GRADUATION -> ProfileFieldFactory(
                        R.string.profile_attribute_university_grad,
                        { info, value -> info.copy(universityGraduation = value.toInt()) },
                        { value -> value.toIntOrNull() ?: -1 > 0 }
                )
                ProfileAttributes.FIELD_DEPARTMENT -> ProfileFieldFactory(
                        R.string.profile_attribute_department,
                        { info, value -> info.copy(department = value) }
                )
                ProfileAttributes.FIELD_WORKPLACE -> ProfileFieldFactory(
                        R.string.profile_attribute_wrokplace,
                        { info, value -> info.copy(currentWork = value) }
                )
                else -> throw RuntimeException("Unsupported user profile attribute with homeworkId: $field")
            }
        }
    }

    fun make(value: String): ProfileField {
        return ProfileField(value, header, apply, validate)
    }
}