package com.example.ntor.presentation.run.started


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class StopRunDialog(
    private val title: String,
    private val okButtonText: String,
    private val cancelButtonText: String,
) : DialogFragment() {

    interface DialogListener {
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }

    private lateinit var listener: DialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder
                .setTitle(this.title)
                .setPositiveButton(
                    this.okButtonText
                ) { _, _ ->
                    listener.onDialogPositiveClick()
                }
                .setNegativeButton(
                    this.cancelButtonText
                ) { _, _ ->
                    listener.onDialogNegativeClick()
                    dialog?.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as DialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                (context.toString() +
                        " must implement Dialog Listener")
            )
        }
    }

}