package com.skosc.tkffintech.misc

//TODO SEE UpdateResult

enum class UpdatingError { UNKNOWN, NO_NETWORK, SERVER_ERROR }

sealed class UpdateStatus {
    object InProgress : UpdateStatus()
    object Success : UpdateStatus()
    data class Error(val error: UpdatingError) : UpdateStatus()
}