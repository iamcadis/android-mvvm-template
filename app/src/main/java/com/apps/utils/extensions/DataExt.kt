package com.apps.utils.extensions

import org.json.JSONException
import org.json.JSONObject
import java.util.regex.Pattern

val String?.isHasHTMLTags: Boolean
    get() {
        if (this == null) return false
        return Pattern
                .compile("<(\"[^\"]*\"|'[^']*'|[^'\">])*>")
                .matcher(this).find()
    }

fun String?.getJsonResponse(key: String): String? {
    if (this == null) return null
    return try {
        JSONObject(this).getString(key)
    } catch (e: JSONException) {
        null
    }
}