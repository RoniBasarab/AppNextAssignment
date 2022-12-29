package com.hit.appnexthomeassignment.models

import okhttp3.Headers

data class BaseDomainModel<T>(
    var successObject: T? = null,
    var errorObject: ErrorMessage? = null,
    var code: Int? = null,
    var headers: Headers? = null

) {
    fun isSuccessful(): Boolean {
        return successObject != null
    }
}
