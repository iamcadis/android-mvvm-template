package com.apps.utils.extensions

import com.apps.constants.Regex
import org.json.JSONException
import org.json.JSONObject

val CharSequence?.isEmailValid: Boolean
    get() {
        if (this.isNullOrBlank()) return false
        return Regex.EMAIL_PATTERN.matcher(this).matches()
    }

val CharSequence?.isPasswordValid: Boolean
    get() {
        if (this.isNullOrBlank()) return false
        return Regex.PASSWORD_PATTERN.matcher(this).matches()
    }

val CharSequence?.isPhoneValid: Boolean
    get() {
        if (this.isNullOrBlank()) return false
        return this.length >= 8
    }

val String?.isHasHTMLTags: Boolean
    get() {
        if (this.isNullOrBlank()) return false
        return Regex.HTML_PATTERN.matcher(this).find()
    }

fun String?.getJsonResponse(key: String): String? {
    if (this.isNullOrBlank()) return null
    return try {
        JSONObject(this).getString(key)
    } catch (e: JSONException) {
        null
    }
}