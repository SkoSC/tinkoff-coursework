package com.skosc.tkffintech.utils

object GlobalConstants {
    object Room {
        const val DB_NAME = "tkf-default-db"
    }

    object URL {
        const val TINKOFF_REGISTER = "https://fintech.tinkoff.ru/register"
        const val TINKOFF_ROOT = "https://tinkoff.ru/"
        const val TINKOFF_FINTECH_ROOT = "https://fintech.tinkoff.ru/"
        const val TINKOFF_API_ENDPOINT = "https://fintech.tinkoff.ru/api/"
    }

    object SharedPrefs {
        object Timers {
            const val NAME = "tkf-timers"
        }

        object UserInfo {
            const val NAME = "tkf-user-info"
            const val KEY_OBJECT = "user-info"
        }
    }

    object COOKIES {
        const val ANYGEN = "anygen"
        const val CSRF_TOKEN = "csrftoken"
    }
}