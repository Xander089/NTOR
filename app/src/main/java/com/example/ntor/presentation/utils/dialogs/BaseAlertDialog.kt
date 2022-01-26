package com.example.ntor.presentation.utils.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.ntor.R
import com.example.ntor.databinding.DialogBinding

class BaseAlertDialog(
    val ok: () -> Unit,
    val cancel: () -> Unit = {},
    private val title: String = "",
    private val message: String = "",
) : DialogFragment() {

    private lateinit var binding: DialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogBinding.inflate(inflater, container, false)
        binding.apply {
            stopWorkoutLabel.text = title
            messageLabel.text = message
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog?.window?.attributes = layoutParams
        val cancelButton = view.findViewById<Button>(R.id.cancelButton)
        val confirmButton = view.findViewById<Button>(R.id.continueButton)

        cancelButton.setOnClickListener {
            cancel()
            dialog?.dismiss()
        }
        confirmButton.setOnClickListener {
            ok()
            dialog?.dismiss()
        }

    }

}