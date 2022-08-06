package com.naram.flo.data.remote.model

import java.lang.Exception

sealed class FloResponse {
    data class Success(val data: Flo): FloResponse()
    data class Error(val error: Exception): FloResponse()
}