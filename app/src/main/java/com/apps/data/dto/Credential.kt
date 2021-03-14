package com.apps.data.dto

import com.squareup.moshi.Json

data class Credential(
    @Json(name = "username")
    var username: String,

    @Json(name = "password")
    var password: String,

    @Json(name = "role")
    var role: String = "MERCHANT"
)