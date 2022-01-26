package com.example.ntor.presentation.utils.dialogs

import androidx.fragment.app.DialogFragment

object DialogFactory {
    fun create(
        flavour: DialogFlavour,
        ok: () -> Unit = {},
        cancel: () -> Unit = {}
    ): DialogFragment {
        return when (flavour) {
            DialogFlavour.STOP_RUN -> StopRunDialogFragment(ok, cancel)
            else -> StopRunDialogFragment(ok, cancel)
        }
    }

}