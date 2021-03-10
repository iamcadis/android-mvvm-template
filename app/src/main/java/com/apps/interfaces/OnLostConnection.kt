package com.apps.interfaces

interface OnLostConnection {
    fun onRetry(passingCode: String?)
}