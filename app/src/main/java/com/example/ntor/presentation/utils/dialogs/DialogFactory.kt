package com.example.ntor.presentation.utils.dialogs

import androidx.fragment.app.DialogFragment

object DialogFactory {
    fun create(
        flavour: DialogFlavour,
        ok: () -> Unit = {},
        cancel: () -> Unit = {},
        title: String = "",
        message: String = "",
    ): DialogFragment {

        return when (flavour) {
            DialogFlavour.BASE_ALERT_DIALOG -> BaseAlertDialog(
                ok,
                cancel,
                title,
                message
            )
        }
    }

}