package com.skosc.tkffintech.entities

import com.google.gson.annotations.SerializedName

/**
 * Expanded user info, generally used to represent current user
 */
data class UserInfo(
        @SerializedName("admin")
        val admin: Boolean = false,
        @SerializedName("avatar")
        val avatar: String = "",
        @SerializedName("birthday")
        val birthday: String = "",
        @SerializedName("current_work")
        val currentWork: String = "",
        @SerializedName("department")
        val department: String = "",
        @SerializedName("description")
        val description: String = "",
        @SerializedName("email")
        val email: String = "",
        @SerializedName("faculty")
        val faculty: String = "",
        @SerializedName("first_name")
        val firstName: String = "",
        @SerializedName("grade")
        val grade: Any? = Any(),
        @SerializedName("id")
        val id: Long = 0,
        @SerializedName("is_client")
        val isClient: Boolean = false,
        @SerializedName("last_name")
        val lastName: String = "",
        @SerializedName("middle_name")
        val middleName: String = "",
        @SerializedName("notifications")
        val notifications: List<Any> = listOf(),
        @SerializedName("phone_mobile")
        val phoneMobile: String = "",
        @SerializedName("region")
        val region: String = "",
        @SerializedName("resume")
        val resume: String = "",
        @SerializedName("school")
        val school: String = "",
        @SerializedName("school_graduation")
        val schoolGraduation: Int = 0,
        @SerializedName("skype_login")
        val skypeLogin: Any? = Any(),
        @SerializedName("t_shirt_size")
        val tShirtSize: String = "",
        @SerializedName("university")
        val university: String = "",
        @SerializedName("university_graduation")
        val universityGraduation: Int = 0
)