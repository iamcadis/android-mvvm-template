package com.apps.utils

import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import com.apps.R
import com.apps.constants.Validation
import com.apps.utils.extensions.isEmailValid
import com.apps.utils.extensions.isPasswordValid
import com.apps.utils.extensions.isPhoneValid
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("validation")
fun setValidation(view: TextInputLayout, rule: Validation) {
    view.editText?.doOnTextChanged { text, _, _, _ ->
        if (rule == Validation.REQUIRED || text.isNullOrBlank()) {
            view.error = view.resources.getString(R.string.field_required)
            return@doOnTextChanged
        }
        if (rule == Validation.EMAIL && !text.isEmailValid) {
            view.error = view.resources.getString(R.string.email_not_valid)
            return@doOnTextChanged
        }
        if (rule == Validation.PASSWORD && !text.isPasswordValid) {
            view.error = view.resources.getString(R.string.password_required_valid_format)
            return@doOnTextChanged
        }
        if (rule == Validation.PHONE_NUMBER && !text.isPhoneValid) {
            view.error = view.resources.getString(R.string.contact_number_not_valid)
            return@doOnTextChanged
        }
        view.error = null
    }
}